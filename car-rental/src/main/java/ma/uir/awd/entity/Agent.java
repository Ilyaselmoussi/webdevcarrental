package ma.uir.awd.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent extends User {

    @Column(unique = true)
    private String employeeCode;

    @OneToMany(mappedBy = "agent")
    private List<RentalContract> rentalContracts = new ArrayList<>();

    public Agent() {
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public List<RentalContract> getRentalContracts() {
        return rentalContracts;
    }

    public void setRentalContracts(List<RentalContract> rentalContracts) {
        this.rentalContracts = rentalContracts;
    }
}
