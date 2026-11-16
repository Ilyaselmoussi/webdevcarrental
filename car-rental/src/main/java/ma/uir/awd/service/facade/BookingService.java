package ma.uir.awd.service.facade;
import ma.uir.awd.entity.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/** Creation et annulation des reservations, controle des conflits compris. */
public interface BookingService {
    Booking create(Long customerId, Long vehicleId, LocalDate startDate,
                   LocalDate endDate, Map<Long, Integer> equipmentQuantities);
    Booking findById(Long bookingId);
    List<Booking> findByCustomer(Long customerId);
    Booking cancel(Long bookingId);
}
