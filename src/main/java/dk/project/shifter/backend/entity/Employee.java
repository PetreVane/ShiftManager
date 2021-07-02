package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "employee")
public class Employee extends AbstractEntity {
    
    @NotNull
    @NotEmpty
    private String firstName = "";

    @NotNull
    @NotEmpty
    private String lastName = "";

    @NotNull
    @NotEmpty
    private String email = "";

//    @OneToMany(mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Company> companies;

//    public void addCompany(Company company) {
//        if (companies == null) {
//            companies = new LinkedList<>();
//        }
//        companies.add(company);
//        company.setEmployee(this);
//    }

    public Employee() { }

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
