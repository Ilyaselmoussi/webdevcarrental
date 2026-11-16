package ma.uir.awd.ws.facade;

import ma.uir.awd.service.facade.InvoiceService;
import ma.uir.awd.ws.converter.DtoConverter;
import ma.uir.awd.ws.converter.RequestValidation;
import ma.uir.awd.ws.dto.InvoiceResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService service;
    public InvoiceController(InvoiceService service) { this.service = service; }

    @GetMapping("/contract/{contractId}")
    public InvoiceResponse byContract(@PathVariable Long contractId) {
        RequestValidation.positiveId(contractId, "contractId");
        return DtoConverter.toDto(service.findByContract(contractId));
    }

    @GetMapping("/customer/{customerId}")
    public List<InvoiceResponse> byCustomer(@PathVariable Long customerId) {
        RequestValidation.positiveId(customerId, "customerId");
        return service.findByCustomer(customerId).stream().map(DtoConverter::toDto).toList();
    }
}
