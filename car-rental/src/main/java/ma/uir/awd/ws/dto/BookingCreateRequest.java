package ma.uir.awd.ws.dto;

import java.time.LocalDate;
import java.util.Map;
public record BookingCreateRequest(Long customerId, Long vehicleId,
        LocalDate startDate, LocalDate endDate,
        Map<Long, Integer> equipmentQuantities) {}
