package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Company;
import dk.project.shifter.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }
    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
