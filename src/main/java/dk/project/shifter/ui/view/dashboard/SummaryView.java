package dk.project.shifter.ui.view.dashboard;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.service.ShiftService;
import dk.project.shifter.exceptions.CustomException;
import dk.project.shifter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "summary", layout = MainLayout.class)
@PageTitle("Hello World")
public class SummaryView extends VerticalLayout {

    private ShiftService shiftService;

    HorizontalLayout formLayout = new HorizontalLayout();
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker("Select end date");
    private Text text = new Text("You have worked: ");
    private TextField textField = new TextField("Total hours for interval");
    private Select select = new Select();
    private Button saveButton = new Button("Fetch records");
    private List<Shift> shifts = new ArrayList<>();

    private Map<String, Duration> durationByType = new HashMap<>();


    public SummaryView(@Autowired ShiftService shiftService) {
        this.shiftService = shiftService;
        addClassName("hello-world-view");

        formLayout.add(configureDatePicker(), endDatePicker );
        add(formLayout, configureSelect(), textField, configureButton());
    }

    private DatePicker configureDatePicker() {
        startDatePicker.setLabel("Select start date");
        return startDatePicker;
    }

    private Button configureButton() {
        saveButton.addClickListener(event -> {
            durationByType.clear();
            getShiftsFor(startDatePicker.getValue(), endDatePicker.getValue());
            getDurationByShiftTypeForInterval(startDatePicker.getValue(), endDatePicker.getValue());
        });
        return saveButton;
    }

    private void getShiftsFor(LocalDate startDate, LocalDate endDate) {
        Duration totalDuration = Duration.ZERO;
        var shifts = shiftService.getShiftsForTimeInterval(startDate, endDate);

        for (Shift shift : shifts) {
            totalDuration = totalDuration.plus(shiftService.getShiftDuration(shift));
        }
        var result = totalDuration.toString().replace("P", "").replace("T", "").replace("H", " hours ").replace("M", " minutes ");
        setTextField(result);
    }

    private void getDurationByShiftTypeForInterval(LocalDate startDate, LocalDate endDate) {
        durationByType = shiftService.mapShiftsForTimeInterval(startDate, endDate);
        System.out.println("Summary duration by type: " + durationByType);
    }

    private Select configureSelect() throws CustomException {
        select.setLabel("Shift type");
        select.setPlaceholder("Click to select");
        select.setItems("Normal", "Overtime", "Holiday");
        select.addValueChangeListener(event -> {
            System.out.println("Event Value " + event.getValue());
            var shiftType = event.getValue().toString().toLowerCase();
            try {
                var shiftDuration = getDurationFor(shiftType);
                System.out.println("Shift duration " + shiftDuration);
                setTextField(shiftDuration);
            }
            catch (Exception e) {
                setTextField(e.getMessage());
            }
        });
        return select;
    }

    private void setTextField(String value) {
        textField.setPlaceholder(value);
    }

    private String getDurationFor(String type) {
        String result = "";
        if (durationByType.get(type) == null) {
            result = "No records";
            throw new CustomException("No records");
        } else {
            result = durationByType.get(type).toString();
        }
        return result;
    }
}