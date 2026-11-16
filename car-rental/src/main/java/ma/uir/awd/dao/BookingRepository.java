package ma.uir.awd.dao;

import ma.uir.awd.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Acces aux donnees de l'entite Booking avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomer_Id(Long customerId);

    List<Booking> findByVehicle_Id(Long vehicleId);

    // Recherche un chevauchement de dates, en tenant compte des statuts actifs.
    // Le service metier choisit les statuts (ex. CONFIRMED, ACTIVE).
    // Les dates de debut/fin sont considerees inclusives.
    @Query("""
            SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
            FROM Booking b
            WHERE b.vehicle.id = :vehicleId
              AND b.status IN :activeStatuses
              AND b.startDate <= :endDate
              AND b.endDate >= :startDate
            """)
    boolean existsOverlappingBooking(
            @Param("vehicleId") Long vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("activeStatuses") Collection<String> activeStatuses
    );
}
