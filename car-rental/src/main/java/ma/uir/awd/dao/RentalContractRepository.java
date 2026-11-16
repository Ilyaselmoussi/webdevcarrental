package ma.uir.awd.dao;

import ma.uir.awd.entity.RentalContract;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

/**
 * Acces aux donnees de l'entite RentalContract avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface RentalContractRepository extends JpaRepository<RentalContract, Long> {

    Optional<RentalContract> findByBooking_Id(Long bookingId);

    List<RentalContract> findByAgent_Id(Long agentId);

    // Historique des retours termines, utile pour le jeu d'entrainement ML.
    List<RentalContract> findByActualReturnDateIsNotNullOrderByActualReturnDateAsc();
}
