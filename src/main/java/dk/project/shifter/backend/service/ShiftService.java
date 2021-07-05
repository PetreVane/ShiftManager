package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.repository.ShiftRepository;
import dk.project.shifter.logic.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private Map<String, Duration> mappedShifts = new HashMap<>();

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

    // Returns a Map of Strings representing the shift type name and an Integer
    // representing the number of shifts for a given type.
    // Ex. : 30 shifts of type Overtime
    public Map<String, Integer> mapShiftsByType() {
        Map<String, Integer> mapped = new HashMap<>();
        List<Shift> shiftList = findAll();
        var types = Shift.HoursType.values();
        for (Shift.HoursType type : types) {
            var counter = ((int) shiftList.stream().filter(shift -> shift.getHoursType().name().equals(type.name())).count());
            mapped.put(type.name(), counter);
        }
        return mapped;
    }

    // Returns a List of shifts for time interval
    public List<Shift> getShiftsForTimeInterval(LocalDate startDate, LocalDate endDate) {
        return shiftRepository.searchShift(startDate, endDate);
    }

    // Returns a Map of shifts as keys and Duration as objects, for a given time interval.
    // It calculates duration for each shift and maps total duration for shift type.
    // Ex.: Total overtime hours from 1 june to 1 july = 20 hours
    public Map<String, Duration> mapShiftsForTimeInterval(LocalDate startDate, LocalDate endDate) {
        List<Shift> shiftList = getShiftsForTimeInterval(startDate, endDate);
        for (Shift shift : shiftList) {
            mapShiftTypeByDuration(shift, getShiftDuration(shift));
        }
        return mappedShifts;
    }

    // Returns Shift Duration for a given shift
    public Duration getShiftDuration(Shift shift) {
        return Calculator.getWorkedHoursFor(shift);
    }

    // Saves total Duration for a given Shift type.
    // Ex.: saves Normal Shift of 8H into a Map. If there is an entry of type Normal,
    // adds the new shift duration to existing entry duration.
    private void mapShiftTypeByDuration(Shift shift, Duration shiftDuration) {
        var shiftType = shift.getHoursType().name();
        var existingRecord = mappedShifts.get(shiftType);
        if (existingRecord == null) {
            mappedShifts.put(shiftType, shiftDuration);
        } else {
            var mappedDuration = mappedShifts.get(shiftType);
            mappedDuration = mappedDuration.plus(shiftDuration);
            mappedShifts.put(shiftType, mappedDuration);
        }
    }

}
