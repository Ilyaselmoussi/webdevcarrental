package ma.uir.awd.service.impl;

import ma.uir.awd.dao.BookingRepository;
import ma.uir.awd.dao.VehicleRepository;
import ma.uir.awd.entity.Vehicle;
import ma.uir.awd.service.facade.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class VehicleServiceImpl implements VehicleService {
    private static final Set<String> BLOCKING_STATUSES = Set.of("CONFIRMED", "ACTIVE");
    private final VehicleRepository vehicles;
    private final BookingRepository bookings;

    public VehicleServiceImpl(VehicleRepository vehicles, BookingRepository bookings) {
        this.vehicles = vehicles;
        this.bookings = bookings;
    }

    @Override public Vehicle findById(Long id) {
        return vehicles.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Vehicule introuvable : " + id));
    }

    @Override public List<Vehicle> findAll() { return vehicles.findAll(); }

    @Override @Transactional
    public Vehicle save(Vehicle vehicle) {
        if (vehicle == null || vehicle.getCategory() == null) {
            throw new IllegalArgumentException("Vehicule et categorie obligatoires");
        }
        if (vehicle.getBrand() == null || vehicle.getBrand().isBlank()
                || vehicle.getModel() == null || vehicle.getModel().isBlank()
                || vehicle.getPlate() == null || vehicle.getPlate().isBlank()) {
            throw new IllegalArgumentException("Marque, modele et immatriculation obligatoires");
        }
        if (!Double.isFinite(vehicle.getDailyRate()) || vehicle.getDailyRate() < 0
                || !Double.isFinite(vehicle.getMileage()) || vehicle.getMileage() < 0) {
            throw new IllegalArgumentException("Prix ou kilometrage invalide");
        }
        if (vehicle.getStatus() == null || vehicle.getStatus().isBlank()) {
            vehicle.setStatus("AVAILABLE");
        }
        return vehicles.save(vehicle);
    }

    @Override public boolean isAvailable(Long vehicleId, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        Vehicle vehicle = findById(vehicleId);
        // Par securite, ne pas proposer un vehicule actuellement loue ou en maintenance.
        if (!"AVAILABLE".equalsIgnoreCase(vehicle.getStatus())) return false;
        return !bookings.existsOverlappingBooking(vehicleId, startDate, endDate, BLOCKING_STATUSES);
    }

    @Override public List<Vehicle> findAvailable(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);
        return vehicles.findAll().stream()
                .filter(v -> isAvailable(v.getId(), startDate, endDate)).toList();
    }

    private static void validateDates(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Periode de location invalide");
        }
    }
}
