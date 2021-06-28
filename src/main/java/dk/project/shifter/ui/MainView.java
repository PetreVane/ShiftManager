package dk.project.shifter.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Company;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A example of Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route("")
public class MainView extends VerticalLayout {

    private final Grid<Shift> grid = new Grid<>(Shift.class);
    private final ShiftService shiftService;

    public MainView(@Autowired ShiftService shiftService) {
        this.shiftService = shiftService;
        addClassName("list-view"); // mainView css styling
        setSizeFull(); // applies to the mainView
        configureGrid();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        ShiftForm shiftForm = new ShiftForm(splitLayout, grid);

        updateGridContent();
        add(splitLayout);
    }

    private void configureGrid() {
        grid.addClassName("contact-grid"); // grid css styling
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("shiftDate", "startingTime", "endingTime", "hadBreak", "hoursType");
        grid.addColumn(shift -> {
            Company company = shift.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    private void updateGridContent() {
        grid.setItems(shiftService.findAll());
    }

}

