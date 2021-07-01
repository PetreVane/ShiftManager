package dk.project.shifter.logic;

import dk.project.shifter.backend.entity.Shift;

import java.time.Duration;
import java.time.LocalTime;

public class Calculator {

    public static Duration getWorkedHoursFor(Shift shift) {
        var totalHours = shift.getEndingTime().minusHours(shift.getStartingTime().getHour());
        if (shift.hadBreak()) { totalHours = totalHours.minusMinutes(30); }

        var totalMinutes = Math.abs(totalHours.getMinute() - shift.getStartingTime().getMinute());
        totalHours = totalHours.minusMinutes(totalMinutes);
        LocalTime result = LocalTime.of(totalHours.getHour(), totalMinutes);
        System.out.println("Total hours worked: " + result);
        return countDuration(result);
    }


    private static Duration countDuration(LocalTime totalHoursForOneDay) {
        Duration totalDuration = Duration.ZERO;
        totalDuration = totalDuration.plusHours(totalHoursForOneDay.getHour());
        totalDuration = totalDuration.plusMinutes(totalHoursForOneDay.getMinute());
        System.out.println("Duration in hours is " + totalDuration);
        return  totalDuration;
    }
}
