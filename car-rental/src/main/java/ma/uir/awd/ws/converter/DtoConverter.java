package ma.uir.awd.ws.converter;

import ma.uir.awd.entity.*;
import ma.uir.awd.ws.dto.*;

/** Ne renvoie que les attributs necessaires en JSON. Evite les cycles JPA et passwordHash. */
public final class DtoConverter {
    private DtoConverter() {}

    public static VehicleResponse toDto(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getBrand(), v.getModel(), v.getPlate(),
                v.getFuelType(), v.getGearbox(), v.getSeats(), v.getDailyRate(),
                v.getMileage(), v.getStatus(), v.getPhoto(),
                v.getCategory() == null ? null : v.getCategory().getId());
    }

    public static CategoryResponse toDto(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
    }

    public static EquipmentResponse toDto(Equipment e) {
        return new EquipmentResponse(e.getId(), e.getName(), e.getDailyPrice());
    }

    public static BookingResponse toDto(Booking b) {
        return new BookingResponse(b.getId(),
                b.getCustomer() == null ? null : b.getCustomer().getId(),
                b.getVehicle() == null ? null : b.getVehicle().getId(),
                b.getStartDate(), b.getEndDate(), b.getStatus());
    }

    public static RentalResponse toDto(RentalContract r) {
        return new RentalResponse(r.getId(),
                r.getBooking() == null ? null : r.getBooking().getId(),
                r.getAgent() == null ? null : r.getAgent().getId(),
                r.getPickupDate(), r.getExpectedReturnDate(), r.getActualReturnDate(),
                r.getMileageStart(), r.getMileageEnd(), r.getFuelStart(), r.getFuelEnd());
    }

    public static InvoiceResponse toDto(Invoice i) {
        return new InvoiceResponse(i.getId(),
                i.getRentalContract() == null ? null : i.getRentalContract().getId(),
                i.getRentalDays(), i.getRentalAmount(), i.getEquipmentCost(),
                i.getLateFees(), i.getVat(), i.getTotal());
    }

    public static MaintenanceResponse toDto(Maintenance m) {
        return new MaintenanceResponse(m.getId(),
                m.getVehicle() == null ? null : m.getVehicle().getId(),
                m.getDate(), m.getType(), m.getCost(), m.getMileage());
    }

    public static SettingsResponse toDto(Settings s) {
        return new SettingsResponse(s.getId(),
                s.getAdministrator() == null ? null : s.getAdministrator().getId(),
                s.getVatRate(), s.getLateFeeRate());
    }
}
