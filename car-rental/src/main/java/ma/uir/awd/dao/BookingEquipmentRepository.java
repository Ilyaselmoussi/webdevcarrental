package ma.uir.awd.dao;

import ma.uir.awd.entity.BookingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Acces aux donnees de l'entite BookingEquipment avec Spring Data JPA.
 * Les methodes CRUD standards sont heritees de JpaRepository.
 */
public interface BookingEquipmentRepository extends JpaRepository<BookingEquipment, Long> {

    List<BookingEquipment> findByBooking_Id(Long bookingId);

    List<BookingEquipment> findByEquipment_Id(Long equipmentId);
}
