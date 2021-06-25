package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class Company extends AbstractEntity {

    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Employee> employees = new LinkedList<>();

}
