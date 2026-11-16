package ma.uir.awd.service.impl;

import ma.uir.awd.dao.BookingRepository;
import ma.uir.awd.dao.CustomerRepository;
import ma.uir.awd.dao.EquipmentRepository;
import ma.uir.awd.entity.Booking;
import ma.uir.awd.entity.BookingEquipment;
import ma.uir.awd.entity.Customer;
import ma.uir.awd.entity.Equipment;
import ma.uir.awd.entity.Vehicle;
import ma.uir.awd.service.facade.BookingService;
import ma.uir.awd.service.facade.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookings;
    private final CustomerRepository customers;
    private final EquipmentRepository equipment;
    private final VehicleService vehicles;

    public BookingServiceImpl(BookingRepository bookings, CustomerRepository customers,
                              EquipmentRepository equipment, VehicleService vehicles) {
        this.bookings = bookings;
        this.customers = customers;
        this.equipment = equipment;
        this.vehicles = vehicles;
    }

    @Override @Transactional
    public Booking create(Long customerId, Long vehicleId, LocalDate startDate,
                          LocalDate endDate, Map<Long, Integer> equipmentQuantities) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Dates invalides");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation dans le passe interdite");
        }
        Customer customer = customers.findById(customerId).orElseThrow(() ->
                new IllegalArgumentException("Client introuvable : " + customerId));
        Vehicle vehicle = vehicles.findById(vehicleId);
        if (!vehicles.isAvailable(vehicleId, startDate, endDate)) {
            throw new IllegalStateException("Vehicule indisponible pendant cette periode");
        }
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setVehicle(vehicle);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setStatus("CONFIRMED");
        if (equipmentQuantities != null) {
            for (Map.Entry<Long, Integer> entry : equipmentQuantities.entrySet()) {
                Integer qty = entry.getValue();
                if (qty == null || qty <= 0) {
                    throw new IllegalArgumentException("Quantite d'equipement invalide");
                }
                Equipment item = equipment.findById(entry.getKey()).orElseThrow(() ->
                        new IllegalArgumentException("Equipement introuvable : " + entry.getKey()));
                if (!Double.isFinite(item.getDailyPrice()) || item.getDailyPrice() < 0) {
                    throw new IllegalArgumentException("Prix d'equipement invalide");
                }
                BookingEquipment line = new BookingEquipment();
                line.setBooking(booking);
                line.setEquipment(item);
                line.setQuantity(qty);
                line.setDailyPrice(item.getDailyPrice()); // prix fige a la reservation
                booking.getBookingEquipments().add(line);
            }
        }
        return bookings.save(booking); // CascadeType.ALL enregistre les lignes
    }

    @Override public Booking findById(Long bookingId) {
        return bookings.findById(bookingId).orElseThrow(() ->
                new IllegalArgumentException("Reservation introuvable : " + bookingId));
    }

    @Override public List<Booking> findByCustomer(Long customerId) {
        return bookings.findByCustomer_Id(customerId);
    }

    @Override @Transactional
    public Booking cancel(Long bookingId) {
        Booking booking = findById(bookingId);
        if (!"CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Seule une reservation confirmee peut etre annulee");
        }
        booking.setStatus("CANCELLED");
        return bookings.save(booking);
    }
}
