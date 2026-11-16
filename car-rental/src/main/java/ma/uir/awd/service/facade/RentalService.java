package ma.uir.awd.service.facade;
import ma.uir.awd.entity.RentalContract;
import java.time.LocalDate;
import java.util.List;

/** Remise puis retour d'un vehicule. Le retour declenche la facturation. */
public interface RentalService {
    RentalContract pickup(Long bookingId, Long agentId, LocalDate pickupDate,
                          double mileageStart, double fuelStart);
    RentalContract returnVehicle(Long contractId, LocalDate actualReturnDate,
                                 double mileageEnd, double fuelEnd);
    List<RentalContract> findByAgent(Long agentId);
}
