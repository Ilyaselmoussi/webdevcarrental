package ma.uir.awd.ws.converter;

import ma.uir.awd.entity.Category;
import ma.uir.awd.entity.Vehicle;
import ma.uir.awd.ws.dto.VehicleCreateRequest;

/** Convertir un formulaire JSON en entite, sans accepter un statut de location du client. */
public final class VehicleConverter {
    private VehicleConverter() {}

    public static Vehicle fromRequest(VehicleCreateRequest request) {
        RequestValidation.present(request, "vehicule");
        RequestValidation.positiveId(request.categoryId(), "categoryId");
        RequestValidation.text(request.brand(), "brand");
        RequestValidation.text(request.model(), "model");
        RequestValidation.text(request.plate(), "plate");
        RequestValidation.nonNegative(request.dailyRate(), "dailyRate");
        RequestValidation.nonNegative(request.mileage(), "mileage");
        if (request.seats() <= 0) throw new IllegalArgumentException("seats doit etre positif");
        Category category = new Category();
        category.setId(request.categoryId()); // Hibernate verifiera la FK a l'insertion
        Vehicle vehicle = new Vehicle();
        vehicle.setCategory(category);
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setPlate(request.plate());
        vehicle.setFuelType(request.fuelType());
        vehicle.setGearbox(request.gearbox());
        vehicle.setSeats(request.seats());
        vehicle.setDailyRate(request.dailyRate());
        vehicle.setMileage(request.mileage());
        vehicle.setPhoto(request.photo());
        vehicle.setStatus("AVAILABLE");
        return vehicle;
    }
}
