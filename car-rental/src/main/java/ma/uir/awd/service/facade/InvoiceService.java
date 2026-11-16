package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Invoice;
import java.util.List;

/** Calcule puis sauvegarde la facture d'un contrat termine. */
public interface InvoiceService {
    Invoice generateForContract(Long contractId);
    Invoice findByContract(Long contractId);
    List<Invoice> findByCustomer(Long customerId);
}
