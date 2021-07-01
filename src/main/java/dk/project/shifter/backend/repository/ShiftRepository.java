package dk.project.shifter.backend.repository;


import dk.project.shifter.backend.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query("select s from Shift s where s.shiftDate between :startDate and :endDate")
    List<Shift> searchShift(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
