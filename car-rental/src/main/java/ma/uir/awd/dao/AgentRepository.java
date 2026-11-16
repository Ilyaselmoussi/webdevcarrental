package ma.uir.awd.dao;

import ma.uir.awd.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Acces aux donnees de l'entite Agent avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByEmployeeCode(String employeeCode);
}
