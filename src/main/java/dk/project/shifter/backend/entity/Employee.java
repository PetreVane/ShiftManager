package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
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
    @NotNull
    private Employee.HoursType hoursType;

}
