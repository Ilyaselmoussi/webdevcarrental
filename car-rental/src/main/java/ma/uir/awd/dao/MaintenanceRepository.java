package ma.uir.awd.dao;

import ma.uir.awd.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Acces aux donnees de l'entite Maintenance avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByVehicle_IdOrderByDateDesc(Long vehicleId);
}
