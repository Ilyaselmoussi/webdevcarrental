package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record PickupRequest(Long bookingId, Long agentId, LocalDate pickupDate,
        double mileageStart, double fuelStart) {}
