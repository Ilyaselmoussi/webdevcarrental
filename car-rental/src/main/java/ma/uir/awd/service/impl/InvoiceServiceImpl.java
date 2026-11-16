package ma.uir.awd.service.impl;

import ma.uir.awd.dao.InvoiceRepository;
import ma.uir.awd.dao.RentalContractRepository;
import ma.uir.awd.entity.Booking;
import ma.uir.awd.entity.BookingEquipment;
import ma.uir.awd.entity.Invoice;
import ma.uir.awd.entity.RentalContract;
import ma.uir.awd.entity.Settings;
import ma.uir.awd.service.facade.InvoiceService;
import ma.uir.awd.service.facade.SettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoices;
    private final RentalContractRepository contracts;
    private final SettingsService settings;

    public InvoiceServiceImpl(InvoiceRepository invoices, RentalContractRepository contracts,
                              SettingsService settings) {
        this.invoices = invoices;
        this.contracts = contracts;
        this.settings = settings;
    }

    @Override @Transactional
    public Invoice generateForContract(Long contractId) {
        // Idempotence : on ne facture pas deux fois le meme contrat.
        var existing = invoices.findByRentalContract_Id(contractId);
        if (existing.isPresent()) return existing.get();
        RentalContract contract = contracts.findById(contractId).orElseThrow(() ->
                new IllegalArgumentException("Contrat introuvable"));
        if (contract.getActualReturnDate() == null) {
            throw new IllegalStateException("Impossible de facturer avant le retour");
        }
        Booking booking = contract.getBooking();
        Settings configuration = settings.getCurrent();

        // Periodes inclusives comme dans la recherche de disponibilite.
        long plannedDays = Math.max(1, ChronoUnit.DAYS.between(
                contract.getPickupDate(), contract.getExpectedReturnDate()) + 1);
        long lateDays = Math.max(0, ChronoUnit.DAYS.between(
                contract.getExpectedReturnDate(), contract.getActualReturnDate()));
        if (plannedDays > Integer.MAX_VALUE) throw new IllegalArgumentException("Location trop longue");
        double rentalAmount = round(plannedDays * booking.getVehicle().getDailyRate());
        double equipmentCost = 0;
        for (BookingEquipment line : booking.getBookingEquipments()) {
            equipmentCost += line.getDailyPrice() * line.getQuantity() * plannedDays;
        }
        equipmentCost = round(equipmentCost);
        // lateFeeRate represente un montant fixe par jour de retard.
        double lateFees = round(lateDays * configuration.getLateFeeRate());
        double subtotal = round(rentalAmount + equipmentCost + lateFees);
        // vatRate : 0.20 correspond a 20 %.
        double vat = round(subtotal * configuration.getVatRate());
        Invoice invoice = new Invoice();
        invoice.setRentalContract(contract);
        invoice.setRentalDays((int) plannedDays);
        invoice.setRentalAmount(rentalAmount);
        invoice.setEquipmentCost(equipmentCost);
        invoice.setLateFees(lateFees);
        invoice.setVat(vat);
        invoice.setTotal(round(subtotal + vat));
        return invoices.save(invoice);
    }

    @Override public Invoice findByContract(Long contractId) {
        return invoices.findByRentalContract_Id(contractId).orElseThrow(() ->
                new IllegalArgumentException("Facture introuvable"));
    }

    @Override public List<Invoice> findByCustomer(Long customerId) {
        return invoices.findByRentalContract_Booking_Customer_Id(customerId);
    }

    private static double round(double amount) {
        if (!Double.isFinite(amount)) throw new IllegalArgumentException("Montant invalide");
        return BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
