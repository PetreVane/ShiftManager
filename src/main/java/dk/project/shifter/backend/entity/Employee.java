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

    @OneToMany(mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Company> companies;

    @Enumerated
    private Employee.HoursType hoursType;

    public void addCompany(Company company) {
        if (companies == null) {
            companies = new LinkedList<>();
        }
        companies.add(company);
        company.setEmployee(this);
    }

    public Employee() { }

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /*
    @ManyToOne
    @JoinColumn(name = "company_id")
 */
}
