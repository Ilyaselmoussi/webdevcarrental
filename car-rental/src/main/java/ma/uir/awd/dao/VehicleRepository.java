package ma.uir.awd.dao;

import ma.uir.awd.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Acces aux donnees de l'entite Vehicle avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCategory_Id(Long categoryId);

    List<Vehicle> findByStatusIgnoreCase(String status);

    List<Vehicle> findByFuelTypeIgnoreCase(String fuelType);

    List<Vehicle> findByDailyRateBetween(double minimum, double maximum);
}
