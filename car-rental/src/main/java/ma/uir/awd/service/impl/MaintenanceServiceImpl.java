package ma.uir.awd.service.impl;

import ma.uir.awd.dao.MaintenanceRepository;
import ma.uir.awd.dao.VehicleRepository;
import ma.uir.awd.entity.Maintenance;
import ma.uir.awd.entity.Vehicle;
import ma.uir.awd.service.facade.MaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository records;
    private final VehicleRepository vehicles;

    public MaintenanceServiceImpl(MaintenanceRepository records, VehicleRepository vehicles) {
        this.records = records;
        this.vehicles = vehicles;
    }

    @Override @Transactional
    public Maintenance register(Long vehicleId, LocalDate date, String type,
                                double cost, double mileage) {
        Vehicle vehicle = findVehicle(vehicleId);
        if (date == null || type == null || type.isBlank()
                || !Double.isFinite(cost) || cost < 0
                || !Double.isFinite(mileage) || mileage < 0) {
            throw new IllegalArgumentException("Informations de maintenance invalides");
        }
        Maintenance entry = new Maintenance();
        entry.setVehicle(vehicle);
        entry.setDate(date);
        entry.setType(type);
        entry.setCost(cost);
        entry.setMileage(mileage);
        return records.save(entry);
    }

    @Override public List<Maintenance> history(Long vehicleId) {
        findVehicle(vehicleId);
        return records.findByVehicle_IdOrderByDateDesc(vehicleId);
    }

    @Override @Transactional
    public void markUnderMaintenance(Long vehicleId) {
        Vehicle vehicle = findVehicle(vehicleId);
        if ("RENTED".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalStateException("Vehicule en cours de location");
        }
        vehicle.setStatus("MAINTENANCE");
        vehicles.save(vehicle);
    }

    @Override @Transactional
    public void markAvailable(Long vehicleId) {
        Vehicle vehicle = findVehicle(vehicleId);
        if (!"MAINTENANCE".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalStateException("Vehicule non marque en maintenance");
        }
        vehicle.setStatus("AVAILABLE");
        vehicles.save(vehicle);
    }

    private Vehicle findVehicle(Long id) {
        return vehicles.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Vehicule introuvable : " + id));
    }
}
