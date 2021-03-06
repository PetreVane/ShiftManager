package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Employee;
import dk.project.shifter.backend.entity.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        addTestData();
    }

    public void addTestData() throws NoSuchAlgorithmException {
        Employee employee = new Employee("Ioana", "Radu", "ioana.radu@email.com");

        // save employee
        employeeService.saveEmployee(employee);

        // randomize hours type
        Random random = SecureRandom.getInstanceStrong();

        Shift tuesdayZara = addNewShift(LocalDate.of(2021, 06, 01), LocalTime.of(8, 30), LocalTime.of(16, 45), true);
        Shift wednesdayZara = addNewShift(LocalDate.of(2021, 06, 02), LocalTime.of(10, 30), LocalTime.of(19, 30), true);
        Shift thursdayZara = addNewShift(LocalDate.of(2021, 06, 03), LocalTime.of(12, 30), LocalTime.of(19, 30), true);
        Shift fridayZara = addNewShift(LocalDate.of(2021, 06, 04), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        Shift tuesdaySostrene = addNewShift(LocalDate.of(2021, 06, 11), LocalTime.of(9, 30), LocalTime.of(16, 45), true);
        Shift wednesdaySostrene = addNewShift(LocalDate.of(2021, 06, 12), LocalTime.of(15, 30), LocalTime.of(19, 30), true);
        Shift thursdaySostrene = addNewShift(LocalDate.of(2021, 06, 23), LocalTime.of(13, 45), LocalTime.of(19, 30), true);
        Shift fridaySostrene = addNewShift(LocalDate.of(2021, 06, 24), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        List<Shift> zaraShifts = List.of(tuesdayZara, wednesdayZara, thursdayZara, fridayZara);
        List<Shift> sostreneShifts = List.of(tuesdaySostrene, wednesdaySostrene, thursdaySostrene, fridaySostrene);

        zaraShifts.stream().forEach(shift -> {
            int randomType = random.nextInt(Shift.HoursType.values().length);
            shift.setHoursType(Shift.HoursType.values()[randomType]);
            shiftService.saveShift(shift);
        });

        sostreneShifts.stream().forEach(shift -> {
            int randomType = random.nextInt(Shift.HoursType.values().length);
            shift.setHoursType(Shift.HoursType.values()[randomType]);
            shiftService.saveShift(shift);
        });

    }

    private Shift addNewShift(LocalDate date, LocalTime startingTime, LocalTime endingTime, Boolean hadBreak) {
        return new Shift(date, startingTime, endingTime, hadBreak);
    }

}


