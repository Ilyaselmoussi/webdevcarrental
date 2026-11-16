package ma.uir.awd.dao;

import ma.uir.awd.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Acces aux donnees de l'entite Administrator avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findByAdminCode(String adminCode);
}
