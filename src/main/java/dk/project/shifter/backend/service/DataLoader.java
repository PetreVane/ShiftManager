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
        Company company = new Company("Zara");

        // save employee
        employee.addCompany(company);
        employeeService.saveEmployee(employee);

        // save company
        company.setEmployee(employee);
        companyService.saveCompany(company);


        System.out.println("Creating shifts now ...");
        Shift tuesdayShift = addNewShift(LocalDate.of(2021, 06, 01), LocalTime.of(8, 30), LocalTime.of(16, 45), true);
        Shift wednesdayShift = addNewShift(LocalDate.of(2021, 06, 02), LocalTime.of(10, 30), LocalTime.of(19, 30), true);
        Shift thursdayShift = addNewShift(LocalDate.of(2021, 06, 03), LocalTime.of(12, 30), LocalTime.of(19, 30), true);
        Shift fridayShift = addNewShift(LocalDate.of(2021, 06, 04), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        List<Shift> shiftList = List.of(tuesdayShift,thursdayShift, wednesdayShift, thursdayShift, fridayShift);
        for (Shift shift: shiftList) {
            company.addShift(shift);
            shift.setCompany(company);
            // save shift
            shiftService.saveShift(shift);
        }
        System.out.println("Test data should have been added by now ...");
    }

    private Shift addNewShift(LocalDate date, LocalTime startingTime, LocalTime endingTime, Boolean hadBreak) {
        return new Shift(date, startingTime, endingTime, hadBreak);
    }
}


