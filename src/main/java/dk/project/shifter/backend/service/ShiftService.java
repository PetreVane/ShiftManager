package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.repository.ShiftRepository;
import dk.project.shifter.logic.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    public void saveShift(Shift shift) {
        shiftRepository.save(shift);
    }

    public Long countShifts() {
        return shiftRepository.count();
    }

    public void deleteShift(Shift shift) {
        shiftRepository.delete(shift);
    }

    public void deleteShiftById(Long id) {
        shiftRepository.deleteById(id);
    }

    public Shift getShift(Long id) {
        return shiftRepository.getOne(id);
    }

    public Map<String, Integer> mapShiftsByType() {
        Map<String, Integer> mapped = new HashMap<>();
        List<Shift> shiftList = findAll();
        var types = Shift.HoursType.values();
        for (Shift.HoursType type : types) {
            var counter = ((int) shiftList.stream().filter(shift -> {
                return shift.getHoursType().name().equals(type.name());
            }).count());
            mapped.put(type.name(), counter);
        }
        return mapped;
    }

    public Map<String, Duration> filterShiftsByTypeForInterval(LocalDate startDate, LocalDate endDate) {
        Map<String, Duration> mapped = new HashMap<>();
        List<Shift> shiftList = getShiftsByDate(startDate,endDate);
        var types = Shift.HoursType.values();
        Duration totalDuration = Duration.ZERO;
        for (Shift.HoursType type : types) {
            var counter = shiftList.stream().filter(shift -> {
                if (shift.getHoursType().name().equals(type.name())) {
                    var hours = Calculator.getWorkedHoursFor(shift);
                    var shiftDuration = Calculator.countDuration(hours);
                    totalDuration.plus(shiftDuration);
                }
            });
            mapped.put(type.name(), totalDuration);
        }
    }

    public List<Shift> getShiftsByDate(LocalDate startDate, LocalDate endDate) {
        return shiftRepository.searchShift(startDate, endDate);
    }

}
