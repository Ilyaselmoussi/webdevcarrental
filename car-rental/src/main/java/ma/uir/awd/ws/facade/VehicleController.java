package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.VehicleService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.VehicleConverter;
import ma.uir.awd.ws.dto.VehicleCreateRequest;
import ma.uir.awd.ws.dto.VehicleResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService service;
    public VehicleController(VehicleService service) { this.service = service; }

    @GetMapping
    public List<VehicleResponse> all() {
        return service.findAll().stream().map(DtoConverter::toDto).toList();
    }

    @GetMapping("/{id}")
    public VehicleResponse byId(@PathVariable Long id) {
        return DtoConverter.toDto(service.findById(id));
    }

    @GetMapping("/available")
    public List<VehicleResponse> available(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return service.findAvailable(from, to).stream().map(DtoConverter::toDto).toList();
    }

    // Endpoint de developpement : ajouter l'authentification AGENT avant tout deploiement.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@RequestBody VehicleCreateRequest request) {
        return DtoConverter.toDto(service.save(VehicleConverter.fromRequest(request)));
    }
}
