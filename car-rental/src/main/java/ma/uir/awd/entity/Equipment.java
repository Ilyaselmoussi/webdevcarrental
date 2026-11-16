package ma.uir.awd.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double dailyPrice;

    @OneToMany(mappedBy = "equipment")
    private List<BookingEquipment> bookingEquipments = new ArrayList<>();

    public Equipment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public List<BookingEquipment> getBookingEquipments() {
        return bookingEquipments;
    }

    public void setBookingEquipments(List<BookingEquipment> bookingEquipments) {
        this.bookingEquipments = bookingEquipments;
    }
}
