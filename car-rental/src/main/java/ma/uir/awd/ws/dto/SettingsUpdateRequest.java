package ma.uir.awd.ws.dto;

public record SettingsUpdateRequest(Long administratorId, double vatRate, double lateFeeRate) {}
