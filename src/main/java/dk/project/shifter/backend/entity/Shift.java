package dk.project.shifter.backend.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class Shift  extends AbstractEntity {

    private LocalDate date;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private LocalTime pauseDuration;
}
