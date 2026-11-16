package ma.uir.awd.dao;

import ma.uir.awd.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Acces aux donnees de l'entite Settings avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Optional<Settings> findByAdministrator_Id(Long administratorId);
}
