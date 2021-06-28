package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ShiftService {

    private Logger logger = Logger.getLogger(ShiftService.class.getName());

    private ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository) {
        logger.info("INFO ======>>> Called bean constructor for ShiftService");
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

    @PostConstruct
    public void addTestData() {
        System.out.println(" ShiftService postConstruct called ");
        Shift tuesdayShift = addNewShift(LocalDate.of(2021, 06, 01), LocalTime.of(8, 30), LocalTime.of(16, 45), true);
        Shift wednesdayShift = addNewShift(LocalDate.of(2021, 06, 02), LocalTime.of(10, 30), LocalTime.of(19, 30), true);
        Shift thursdayShift = addNewShift(LocalDate.of(2021, 06, 03), LocalTime.of(12, 30), LocalTime.of(19, 30), true);
        Shift fridayShift = addNewShift(LocalDate.of(2021, 06, 04), LocalTime.of(10, 30), LocalTime.of(15, 30), false);

        List<Shift> shiftList = List.of(tuesdayShift,thursdayShift, wednesdayShift, thursdayShift, fridayShift);
        for (Shift shift: shiftList) {
            shiftRepository.save(shift);
        }
    }

    private Shift addNewShift(LocalDate date, LocalTime startingTime, LocalTime endingTime, Boolean hadBreak) {
        return new Shift(date, startingTime, endingTime, hadBreak);
    }

}
