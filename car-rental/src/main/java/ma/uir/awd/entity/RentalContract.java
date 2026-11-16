package ma.uir.awd.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rental_contracts")
public class RentalContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate pickupDate;

    @Column(nullable = false)
    private LocalDate expectedReturnDate;

    private LocalDate actualReturnDate;
    private double mileageStart;
    private double mileageEnd;
    private double fuelStart;
    private double fuelEnd;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToOne(mappedBy = "rentalContract")
    private Invoice invoice;

    public RentalContract() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public double getMileageStart() {
        return mileageStart;
    }

    public void setMileageStart(double mileageStart) {
        this.mileageStart = mileageStart;
    }

    public double getMileageEnd() {
        return mileageEnd;
    }

    public void setMileageEnd(double mileageEnd) {
        this.mileageEnd = mileageEnd;
    }

    public double getFuelStart() {
        return fuelStart;
    }

    public void setFuelStart(double fuelStart) {
        this.fuelStart = fuelStart;
    }

    public double getFuelEnd() {
        return fuelEnd;
    }

    public void setFuelEnd(double fuelEnd) {
        this.fuelEnd = fuelEnd;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
