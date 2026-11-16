package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record MaintenanceCreateRequest(LocalDate date, String type, double cost, double mileage) {}
