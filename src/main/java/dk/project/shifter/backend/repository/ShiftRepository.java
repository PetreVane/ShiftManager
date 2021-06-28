package dk.project.shifter.backend.repository;


import dk.project.shifter.backend.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShiftRepository extends JpaRepository<Shift, Long> { }
