package ma.uir.awd.dao;

import ma.uir.awd.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

/**
 * Acces aux donnees de l'entite Invoice avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByRentalContract_Id(Long rentalContractId);

    List<Invoice> findByRentalContract_Booking_Customer_Id(Long customerId);
}
