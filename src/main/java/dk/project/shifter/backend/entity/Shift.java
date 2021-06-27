package dk.project.shifter.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Shift extends AbstractEntity {

//    @NonNull
//    @NotEmpty
    @Column(name = "shift_date")
    private LocalDate shiftDate;

//    @NotNull
//    @NotEmpty
    @Column(name = "starting_time")
    private LocalTime startingTime;

//    @NotNull
//    @NotEmpty
    @Column(name = "ending_time")
    private LocalTime endingTime;

//    @NotNull
//    @NotEmpty
    @Column(name = "had_break")
    private boolean hadBreak;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
