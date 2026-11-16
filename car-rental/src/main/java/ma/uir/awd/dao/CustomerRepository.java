package ma.uir.awd.dao;

import ma.uir.awd.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Acces aux donnees de l'entite Customer avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLicenseNumber(String licenseNumber);
}
