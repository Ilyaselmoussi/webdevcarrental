package ma.uir.awd.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "administrators")
public class Administrator extends User {

    @Column(unique = true)
    private String adminCode;

    @OneToOne(mappedBy = "administrator")
    private Settings settings;

    public Administrator() {
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
