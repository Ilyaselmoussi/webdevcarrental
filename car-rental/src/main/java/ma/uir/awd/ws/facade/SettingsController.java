package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.SettingsService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.SettingsResponse;
import ma.uir.awd.ws.dto.SettingsUpdateRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {
    private final SettingsService service;
    public SettingsController(SettingsService service) { this.service = service; }

    @GetMapping
    public SettingsResponse current() {
        return DtoConverter.toDto(service.getCurrent());
    }

    // Endpoint ADMIN de developpement. A securiser avec une verification du role ADMIN.
    @PutMapping
    public SettingsResponse update(@RequestBody SettingsUpdateRequest request) {
        RequestValidation.present(request, "parametres");
        RequestValidation.positiveId(request.administratorId(), "administratorId");
        RequestValidation.nonNegative(request.lateFeeRate(), "lateFeeRate");
        if (!Double.isFinite(request.vatRate()) || request.vatRate() < 0 || request.vatRate() > 1) {
            throw new IllegalArgumentException("vatRate doit etre compris entre 0 et 1");
        }
        return DtoConverter.toDto(service.update(request.administratorId(),
                request.vatRate(), request.lateFeeRate()));
    }
}
