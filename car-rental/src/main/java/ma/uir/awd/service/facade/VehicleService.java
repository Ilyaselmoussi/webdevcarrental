package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Vehicle;
import java.time.LocalDate;
import java.util.List;

/** Regles de gestion du parc et disponibilite sur une periode. */
public interface VehicleService {
    Vehicle findById(Long id);
    List<Vehicle> findAll();
    Vehicle save(Vehicle vehicle);
    boolean isAvailable(Long vehicleId, LocalDate startDate, LocalDate endDate);
    List<Vehicle> findAvailable(LocalDate startDate, LocalDate endDate);
}
