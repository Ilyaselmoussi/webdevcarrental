package ma.uir.awd.ws.dto;

import java.time.LocalDate;
public record ReturnRequest(LocalDate actualReturnDate, double mileageEnd, double fuelEnd) {}
