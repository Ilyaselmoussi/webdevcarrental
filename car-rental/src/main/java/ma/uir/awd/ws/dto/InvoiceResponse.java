package ma.uir.awd.ws.dto;

public record InvoiceResponse(Long id, Long rentalContractId, int rentalDays,
        double rentalAmount, double equipmentCost, double lateFees, double vat, double total) {}
