package ma.uir.awd.ws.dto;

public record VehicleResponse(Long id, String brand, String model, String plate,
        String fuelType, String gearbox, int seats, double dailyRate, double mileage,
        String status, String photo, Long categoryId) {}
