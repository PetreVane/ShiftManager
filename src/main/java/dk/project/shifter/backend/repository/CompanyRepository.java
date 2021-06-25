package dk.project.shifter.backend.repository;

import dk.project.shifter.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {  }
