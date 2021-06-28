package dk.project.shifter.backend.service;

import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
