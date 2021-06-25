package dk.project.shifter.logic;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
public class Calculator {

    private LocalDate date;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private boolean hadBreak;

    public LocalTime getWorkedHoursFor(LocalDate date, boolean hadBreak) {
        var totalHours = endingTime.minusHours(startingTime.getHour());
        if (hadBreak) {
            totalHours = totalHours.minusMinutes(30);
        }

        var totalMinutes = Math.abs(totalHours.getMinute() - startingTime.getMinute());
        totalHours = totalHours.minusMinutes(totalMinutes);
        LocalTime result = LocalTime.of(totalHours.getHour(), totalMinutes);
        return result;
    }

    private  void convertTime() {
        LocalTime friday = getWorkedHours();
        LocalTime saturday = getWorkedHours();
        int totalHours = friday.getHour() + saturday.getHour();
        int totalMinutes = friday.getMinute() + saturday.getMinute();
        var hoursConverted = totalMinutes / 60;
        var extraMinutes = totalMinutes % 60;
        totalHours = totalHours + hoursConverted;

        System.out.println("Total worked hours is " + totalHours + " hours and " + extraMinutes + " minutes");
    }
}
