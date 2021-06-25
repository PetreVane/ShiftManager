package dk.project.shifter.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Shift extends AbstractEntity {

    @NonNull
    @NotEmpty
    private LocalDate date;

    @NotNull
    @NotEmpty
    private LocalTime startingTime;

    @NotNull
    @NotEmpty
    private LocalTime endingTime;

    @NotNull
    @NotEmpty
    private LocalTime pauseDuration;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
