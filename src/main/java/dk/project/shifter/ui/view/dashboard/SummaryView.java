package dk.project.shifter.ui.view.dashboard;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.service.ShiftService;
import dk.project.shifter.logic.Calculator;
import dk.project.shifter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "summary", layout = MainLayout.class)
@PageTitle("Hello World")
public class SummaryView extends VerticalLayout {

    private ShiftService shiftService;

    HorizontalLayout formLayout = new HorizontalLayout();
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker("Select end date");
    private Text text = new Text("You have worked: ");
    private TextField totalTextField = new TextField("Total hours for interval");
    private Button saveButton = new Button("Fetch records");
    private List<Shift> shifts = new ArrayList<>();


    public SummaryView(@Autowired ShiftService shiftService) {
        this.shiftService = shiftService;
        addClassName("hello-world-view");
        configureAction();
        formLayout.add(configureDatePicker(), endDatePicker );
        add(formLayout, totalTextField, saveButton);
    }

    private DatePicker configureDatePicker() {
        startDatePicker.setLabel("Select start date");
        return startDatePicker;
    }

    private void configureAction() {
        saveButton.addClickListener(event -> getShiftsFor(startDatePicker.getValue(), endDatePicker.getValue()));
    }

    private void getShiftsFor(LocalDate startDate, LocalDate endDate) {
        Duration totalDuration = Duration.ZERO;
        var shifts = shiftService.getShiftsByDate(startDate, endDate);

        for (Shift shift : shifts) {
            totalDuration = totalDuration.plus(Calculator.getWorkedHoursFor(shift));
        }
        var result = totalDuration.toString().replace("P", "").replace("T", "").replace("H", " hours ").replace("M", " minutes ");
        totalTextField.setPlaceholder(result);
    }

    private void getShiftsByType(LocalDate startDate, LocalDate endDate) {
        var shifts = shiftService.getShiftsByDate(startDate, endDate);

    }

}