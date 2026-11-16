package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.MaintenanceService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.MaintenanceCreateRequest;
import ma.uir.awd.ws.dto.MaintenanceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    private final MaintenanceService service;
    public MaintenanceController(MaintenanceService service) { this.service = service; }

    @GetMapping("/vehicle/{vehicleId}")
    public List<MaintenanceResponse> byVehicle(@PathVariable Long vehicleId) {
        RequestValidation.positiveId(vehicleId, "vehicleId");
        return service.history(vehicleId).stream().map(DtoConverter::toDto).toList();
    }

    // Endpoint agent de developpement : a securiser avant deploiement.
    @PostMapping("/vehicle/{vehicleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponse register(@PathVariable Long vehicleId,
                                        @RequestBody MaintenanceCreateRequest request) {
        RequestValidation.positiveId(vehicleId, "vehicleId");
        RequestValidation.present(request, "maintenance");
        RequestValidation.text(request.type(), "type");
        RequestValidation.nonNegative(request.cost(), "cost");
        RequestValidation.nonNegative(request.mileage(), "mileage");
        return DtoConverter.toDto(service.register(vehicleId, request.date(),
                request.type(), request.cost(), request.mileage()));
    }

    @PostMapping("/vehicle/{vehicleId}/start")
    public Map<String, String> start(@PathVariable Long vehicleId) {
        service.markUnderMaintenance(vehicleId);
        return Map.of("status", "UNDER_MAINTENANCE");
    }

    @PostMapping("/vehicle/{vehicleId}/finish")
    public Map<String, String> finish(@PathVariable Long vehicleId) {
        service.markAvailable(vehicleId);
        return Map.of("status", "AVAILABLE");
    }
}
