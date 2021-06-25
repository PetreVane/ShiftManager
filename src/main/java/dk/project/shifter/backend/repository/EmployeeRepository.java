package dk.project.shifter.backend.repository;

import dk.project.shifter.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {  }
