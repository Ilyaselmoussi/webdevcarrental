package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.BookingService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.BookingCreateRequest;
import ma.uir.awd.ws.dto.BookingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }

    @GetMapping("/{id}")
    public BookingResponse byId(@PathVariable Long id) {
        return DtoConverter.toDto(service.findById(id));
    }

    @GetMapping
    public List<BookingResponse> byCustomer(@RequestParam Long customerId) {
        RequestValidation.positiveId(customerId, "customerId");
        return service.findByCustomer(customerId).stream().map(DtoConverter::toDto).toList();
    }

    // TODO security: derive customerId from authenticated user, never trust body in production.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@RequestBody BookingCreateRequest request) {
        RequestValidation.present(request, "reservation");
        RequestValidation.positiveId(request.customerId(), "customerId");
        RequestValidation.positiveId(request.vehicleId(), "vehicleId");
        return DtoConverter.toDto(service.create(request.customerId(), request.vehicleId(),
                request.startDate(), request.endDate(), request.equipmentQuantities()));
    }

    @PostMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id) {
        return DtoConverter.toDto(service.cancel(id));
    }
}
