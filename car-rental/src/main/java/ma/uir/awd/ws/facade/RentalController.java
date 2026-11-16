package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.RentalService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.PickupRequest;
import ma.uir.awd.ws.dto.RentalResponse;
import ma.uir.awd.ws.dto.ReturnRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService service;
    public RentalController(RentalService service) { this.service = service; }

    @GetMapping("/agent/{agentId}")
    public List<RentalResponse> byAgent(@PathVariable Long agentId) {
        RequestValidation.positiveId(agentId, "agentId");
        return service.findByAgent(agentId).stream().map(DtoConverter::toDto).toList();
    }

    // Endpoint agent de developpement : a securiser avant deploiement.
    @PostMapping("/pickup")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponse pickup(@RequestBody PickupRequest request) {
        RequestValidation.present(request, "depart");
        RequestValidation.positiveId(request.bookingId(), "bookingId");
        RequestValidation.positiveId(request.agentId(), "agentId");
        RequestValidation.nonNegative(request.mileageStart(), "mileageStart");
        RequestValidation.fuel(request.fuelStart(), "fuelStart");
        return DtoConverter.toDto(service.pickup(request.bookingId(), request.agentId(),
                request.pickupDate(), request.mileageStart(), request.fuelStart()));
    }

    @PostMapping("/{contractId}/return")
    public RentalResponse returnVehicle(@PathVariable Long contractId, @RequestBody ReturnRequest request) {
        RequestValidation.present(request, "retour");
        RequestValidation.nonNegative(request.mileageEnd(), "mileageEnd");
        RequestValidation.fuel(request.fuelEnd(), "fuelEnd");
        return DtoConverter.toDto(service.returnVehicle(contractId, request.actualReturnDate(),
                request.mileageEnd(), request.fuelEnd()));
    }
}
