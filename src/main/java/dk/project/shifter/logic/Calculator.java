package dk.project.shifter.logic;

import dk.project.shifter.backend.entity.Shift;

import java.time.Duration;
import java.time.LocalTime;

public class Calculator {

    public static LocalTime getWorkedHoursFor(Shift shift) {
        var totalHours = shift.getEndingTime().minusHours(shift.getStartingTime().getHour());
        if (shift.hadBreak()) { totalHours = totalHours.minusMinutes(30); }

        var totalMinutes = Math.abs(totalHours.getMinute() - shift.getStartingTime().getMinute());
        totalHours = totalHours.minusMinutes(totalMinutes);
        LocalTime result = LocalTime.of(totalHours.getHour(), totalMinutes);
        System.out.println("Total hours worked: " + result);
        countDuration(result);
        return result;
    }

//    public static double countWorkedHours(LocalTime totalHoursForOneDay) {
//        double totalHours = totalHoursForOneDay.getHour();
//        double totalMinutes = totalHoursForOneDay.getMinute();
//        double hoursConverted = totalMinutes / 60;
//        var extraMinutes = totalMinutes % 60;
//        System.out.println("Total worked hours is " + totalHours + " hours and " + extraMinutes + " minutes");
//        return totalHours = totalHours + hoursConverted;
//    }

    public static Duration countDuration(LocalTime totalHoursForOneDay) {
        Duration totalDuration = Duration.ZERO;
        totalDuration = Duration.ofHours(totalHoursForOneDay.getHour());
        totalDuration = Duration.ofMinutes(totalHoursForOneDay.getMinute());
        System.out.println("Duration in hours is " + totalDuration);
        return  totalDuration;
    }
}
