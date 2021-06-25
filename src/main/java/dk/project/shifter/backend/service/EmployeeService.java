package dk.project.shifter.backend.service;

import dk.project.shifter.backend.repository.CompanyRepository;
import dk.project.shifter.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmployeeService {

    private Logger logger = Logger.getLogger(EmployeeService.class.getName());

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }


}
