package ma.uir.awd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "agency_settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double vatRate;
    private double lateFeeRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id", unique = true)
    private Administrator administrator;

    public Settings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public double getLateFeeRate() {
        return lateFeeRate;
    }

    public void setLateFeeRate(double lateFeeRate) {
        this.lateFeeRate = lateFeeRate;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}
