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
        Shift sundayShift = new Shift();
        sundayShift.setShiftDate(LocalDate.now());
        sundayShift.setStartingTime(LocalTime.of(10, 00));
        sundayShift.setHadBreak(true);
        sundayShift.setEndingTime(LocalTime.of(17, 30));

        shiftRepository.save(sundayShift);
    }

}
