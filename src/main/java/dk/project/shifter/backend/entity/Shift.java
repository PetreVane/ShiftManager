package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Shift extends AbstractEntity {


    @Column(name = "shift_date")
    private LocalDate shiftDate;


    @Column(name = "starting_time")
    private LocalTime startingTime;


    @Column(name = "ending_time")
    private LocalTime endingTime;


    @Column(name = "had_break")
    private boolean hadBreak;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Shift() { }

    public Shift(LocalDate shiftDate, LocalTime startingTime, LocalTime endingTime, boolean hadBreak) {
        this.shiftDate = shiftDate;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.hadBreak = hadBreak;
    }
}
