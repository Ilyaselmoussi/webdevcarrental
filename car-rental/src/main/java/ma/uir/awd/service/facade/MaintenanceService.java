package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Maintenance;
import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {
    Maintenance register(Long vehicleId, LocalDate date, String type,
                         double cost, double mileage);
    List<Maintenance> history(Long vehicleId);
    void markUnderMaintenance(Long vehicleId);
    void markAvailable(Long vehicleId);
}
