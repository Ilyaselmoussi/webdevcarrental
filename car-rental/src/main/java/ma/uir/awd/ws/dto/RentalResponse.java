package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record RentalResponse(Long id, Long bookingId, Long agentId,
        LocalDate pickupDate, LocalDate expectedReturnDate, LocalDate actualReturnDate,
        double mileageStart, double mileageEnd, double fuelStart, double fuelEnd) {}
