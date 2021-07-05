package dk.project.shifter.logic;

import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.exceptions.CustomException;

import java.time.Duration;
import java.time.LocalTime;

public class Calculator {

    private Calculator() {
        throw new CustomException("Utility class");
    }

    public static Duration getWorkedHoursFor(Shift shift) {
        var totalHours = shift.getEndingTime().minusHours(shift.getStartingTime().getHour());
        if (shift.hadBreak()) { totalHours = totalHours.minusMinutes(30); }

        var totalMinutes = Math.abs(totalHours.getMinute() - shift.getStartingTime().getMinute());
        totalHours = totalHours.minusMinutes(totalMinutes);
        LocalTime result = LocalTime.of(totalHours.getHour(), totalMinutes);
        return countDuration(result);
    }


    private static Duration countDuration(LocalTime totalHoursForOneDay) {
        Duration totalDuration = Duration.ZERO;
        totalDuration = totalDuration.plusHours(totalHoursForOneDay.getHour());
        totalDuration = totalDuration.plusMinutes(totalHoursForOneDay.getMinute());
        return  totalDuration;
    }
}
