package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Employee;
import dk.project.shifter.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

}
