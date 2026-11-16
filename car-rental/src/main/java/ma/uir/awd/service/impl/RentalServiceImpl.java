package ma.uir.awd.service.impl;

import ma.uir.awd.dao.AgentRepository;
import ma.uir.awd.dao.BookingRepository;
import ma.uir.awd.dao.RentalContractRepository;
import ma.uir.awd.dao.VehicleRepository;
import ma.uir.awd.entity.Agent;
import ma.uir.awd.entity.Booking;
import ma.uir.awd.entity.RentalContract;
import ma.uir.awd.entity.Vehicle;
import ma.uir.awd.service.facade.InvoiceService;
import ma.uir.awd.service.facade.RentalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RentalServiceImpl implements RentalService {
    private final RentalContractRepository contracts;
    private final BookingRepository bookings;
    private final AgentRepository agents;
    private final VehicleRepository vehicles;
    private final InvoiceService invoices;

    public RentalServiceImpl(RentalContractRepository contracts, BookingRepository bookings,
                             AgentRepository agents, VehicleRepository vehicles, InvoiceService invoices) {
        this.contracts = contracts;
        this.bookings = bookings;
        this.agents = agents;
        this.vehicles = vehicles;
        this.invoices = invoices;
    }

    @Override @Transactional
    public RentalContract pickup(Long bookingId, Long agentId, LocalDate pickupDate,
                                 double mileageStart, double fuelStart) {
        Booking booking = bookings.findById(bookingId).orElseThrow(() ->
                new IllegalArgumentException("Reservation introuvable"));
        Agent agent = agents.findById(agentId).orElseThrow(() ->
                new IllegalArgumentException("Agent introuvable"));
        if (!"CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Reservation non confirmee");
        }
        if (contracts.findByBooking_Id(bookingId).isPresent()) {
            throw new IllegalStateException("Contrat deja cree pour cette reservation");
        }
        if (pickupDate == null || pickupDate.isBefore(booking.getStartDate())
                || pickupDate.isAfter(booking.getEndDate())) {
            throw new IllegalArgumentException("Date de depart hors de la periode reservee");
        }
        validateMileageAndFuel(mileageStart, fuelStart);
        Vehicle vehicle = booking.getVehicle();
        if (!"AVAILABLE".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalStateException("Vehicule non disponible au depart");
        }
        RentalContract contract = new RentalContract();
        contract.setBooking(booking);
        contract.setAgent(agent);
        contract.setPickupDate(pickupDate);
        contract.setExpectedReturnDate(booking.getEndDate());
        contract.setMileageStart(mileageStart);
        contract.setFuelStart(fuelStart);
        booking.setStatus("ACTIVE");
        vehicle.setStatus("RENTED");
        contracts.save(contract);
        bookings.save(booking);
        vehicles.save(vehicle);
        return contract;
    }

    @Override @Transactional
    public RentalContract returnVehicle(Long contractId, LocalDate actualReturnDate,
                                        double mileageEnd, double fuelEnd) {
        RentalContract contract = contracts.findById(contractId).orElseThrow(() ->
                new IllegalArgumentException("Contrat introuvable"));
        if (contract.getActualReturnDate() != null) {
            throw new IllegalStateException("Retour deja enregistre");
        }
        if (actualReturnDate == null || actualReturnDate.isBefore(contract.getPickupDate())) {
            throw new IllegalArgumentException("Date de retour invalide");
        }
        validateMileageAndFuel(mileageEnd, fuelEnd);
        if (mileageEnd < contract.getMileageStart()) {
            throw new IllegalArgumentException("Le kilometrage final ne peut pas diminuer");
        }
        contract.setActualReturnDate(actualReturnDate);
        contract.setMileageEnd(mileageEnd);
        contract.setFuelEnd(fuelEnd);
        Booking booking = contract.getBooking();
        booking.setStatus("COMPLETED");
        Vehicle vehicle = booking.getVehicle();
        vehicle.setMileage(mileageEnd);
        vehicle.setStatus("AVAILABLE");
        contracts.save(contract);
        bookings.save(booking);
        vehicles.save(vehicle);
        invoices.generateForContract(contractId); // meme transaction, facture automatique
        return contract;
    }

    @Override public List<RentalContract> findByAgent(Long agentId) {
        return contracts.findByAgent_Id(agentId);
    }

    private static void validateMileageAndFuel(double mileage, double fuel) {
        if (!Double.isFinite(mileage) || mileage < 0
                || !Double.isFinite(fuel) || fuel < 0 || fuel > 100) {
            throw new IllegalArgumentException("Kilometrage ou carburant invalide (0..100%)");
        }
    }
}
