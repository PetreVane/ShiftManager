package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Company extends AbstractEntity {

    @NotNull(message = "Company name needs at least 3 charactes")
    private String name;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Shift> shiftList;

    public Company() { }

    public Company(String name) {
        this.name = name;
    }

//    public void addShift(Shift shift) {
//        if (shiftList == null) {
//            shiftList = new LinkedList<>();
//        }
//        shiftList.add(shift);
//    }
}
