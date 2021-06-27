package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class Employee extends AbstractEntity {

    public enum HoursType {
        mandatory,
        overtime,
        weekend
    }

    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    @NotNull
    @NotEmpty
    private String email = "";

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated
    private Employee.HoursType hoursType;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Shift> shiftList;

    public void addShift(Shift shift) {
        if (shiftList == null) {
            shiftList = new LinkedList<>();
        }
        shiftList.add(shift);
        shift.setEmployee(this);
    }

}
