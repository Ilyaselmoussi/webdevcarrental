package ma.uir.awd.dao;

import ma.uir.awd.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Acces aux donnees de l'entite Equipment avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByNameIgnoreCase(String name);
}
