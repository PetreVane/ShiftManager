package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Company;
import dk.project.shifter.backend.entity.Employee;
import dk.project.shifter.backend.entity.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Adding Test data now ...");
        addTestData();
    }

    public void addTestData() {
        System.out.println("Creating an user now ...");
        Employee employee = new Employee("Ioana", "Radu", "ioana.radu@email.com");

        System.out.println("Creating a company now ...");
        Company zara = new Company("Zara");
        Company sostreneGren = new Company("Søstrene Grene");

        // save employee
        employee.addCompany(zara);
        employee.addCompany(sostreneGren);
        employeeService.saveEmployee(employee);

        // save company
        zara.setEmployee(employee);
        companyService.saveCompany(zara);

        // randomize hours type
        Random random = new Random();

        System.out.println("Creating shifts now ...");
        Shift tuesdayZara = addNewShift(LocalDate.of(2021, 06, 01), LocalTime.of(8, 30), LocalTime.of(16, 45), true);
        Shift wednesdayZara = addNewShift(LocalDate.of(2021, 06, 02), LocalTime.of(10, 30), LocalTime.of(19, 30), true);
        Shift thursdayZara = addNewShift(LocalDate.of(2021, 06, 03), LocalTime.of(12, 30), LocalTime.of(19, 30), true);
        Shift fridayZara = addNewShift(LocalDate.of(2021, 06, 04), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        Shift tuesdaySostrene = addNewShift(LocalDate.of(2020, 07, 01), LocalTime.of(9, 30), LocalTime.of(16, 45), true);
        Shift wednesdaySostrene = addNewShift(LocalDate.of(2020, 07, 02), LocalTime.of(15, 30), LocalTime.of(19, 30), true);
        Shift thursdaySostrene = addNewShift(LocalDate.of(2020, 07, 03), LocalTime.of(13, 45), LocalTime.of(19, 30), true);
        Shift fridaySostrene = addNewShift(LocalDate.of(2020, 07, 04), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        List<Shift> zaraShifts = List.of(tuesdayZara, wednesdayZara, thursdayZara, fridayZara);
        List<Shift> sostreneShifts = List.of(tuesdaySostrene, wednesdaySostrene, thursdaySostrene, fridaySostrene);

        zaraShifts.stream().forEach(shift -> {
            zara.addShift(shift);
            shift.setCompany(zara);
            int randomType = random.nextInt(Shift.HoursType.values().length);
            shift.setHoursType(Shift.HoursType.values()[randomType]);
            shiftService.saveShift(shift);
        });

        sostreneShifts.stream().forEach(shift -> {
            sostreneGren.addShift(shift);
            shift.setCompany(sostreneGren);
            int randomType = random.nextInt(Shift.HoursType.values().length);
            shift.setHoursType(Shift.HoursType.values()[randomType]);
            shiftService.saveShift(shift);
        });

    }

    private Shift addNewShift(LocalDate date, LocalTime startingTime, LocalTime endingTime, Boolean hadBreak) {
        return new Shift(date, startingTime, endingTime, hadBreak);
    }

}


