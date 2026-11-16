package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record MaintenanceResponse(Long id, Long vehicleId, LocalDate date,
        String type, double cost, double mileage) {}
