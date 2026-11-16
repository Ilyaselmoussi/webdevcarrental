package ma.uir.awd.ws.dto;

public record VehicleCreateRequest(String brand, String model, String plate,
        String fuelType, String gearbox, int seats, double dailyRate,
        double mileage, String photo, Long categoryId) {}
