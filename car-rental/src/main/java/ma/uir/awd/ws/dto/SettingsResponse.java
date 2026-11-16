package ma.uir.awd.ws.dto;

public record SettingsResponse(Long id, Long administratorId,
        double vatRate, double lateFeeRate) {}
