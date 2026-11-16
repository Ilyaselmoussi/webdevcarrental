package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record BookingResponse(Long id, Long customerId, Long vehicleId,
        LocalDate startDate, LocalDate endDate, String status) {}
