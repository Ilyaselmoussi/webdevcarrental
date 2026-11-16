package ma.uir.awd.ws.facade;

import ma.uir.awd.entity.Category;
import ma.uir.awd.entity.Equipment;
import ma.uir.awd.service.facade.CatalogService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {
    private final CatalogService service;
    public CatalogController(CatalogService service) { this.service = service; }

    @GetMapping("/categories")
    public List<CategoryResponse> categories() {
        return service.categories().stream().map(DtoConverter::toDto).toList();
    }

    @GetMapping("/equipment")
    public List<EquipmentResponse> equipment() {
        return service.equipment().stream().map(DtoConverter::toDto).toList();
    }

    // Endpoint admin de developpement : a securiser avec authentification + autorisation.
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryCreateRequest request) {
        RequestValidation.present(request, "categorie");
        RequestValidation.text(request.name(), "name");
        Category c = new Category();
        c.setName(request.name());
        return DtoConverter.toDto(service.saveCategory(c));
    }

    // Endpoint admin de developpement : a securiser avec authentification + autorisation.
    @PostMapping("/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentResponse addEquipment(@RequestBody EquipmentCreateRequest request) {
        RequestValidation.present(request, "equipement");
        RequestValidation.text(request.name(), "name");
        RequestValidation.nonNegative(request.dailyPrice(), "dailyPrice");
        Equipment e = new Equipment();
        e.setName(request.name());
        e.setDailyPrice(request.dailyPrice());
        return DtoConverter.toDto(service.saveEquipment(e));
    }
}
