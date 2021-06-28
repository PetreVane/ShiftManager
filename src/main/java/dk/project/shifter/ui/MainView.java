package dk.project.shifter.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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
    private ShiftService shiftService;

    public MainView(@Autowired ShiftService shiftService) {
        this.shiftService = shiftService;
        addClassName("list-view"); // mainView css styling
        setSizeFull(); // applies to the mainView
        configureGrid();
        updateGridContent();
        add(grid);
    }

    private void configureGrid() {
        grid.addClassName("contact-grid"); // grid css styling
        grid.setSizeFull();
        grid.setColumns("shiftDate", "startingTime", "endingTime", "hadBreak");
//        grid.removeColumn("employee");
    }

    private void updateGridContent() {
        grid.setItems(shiftService.findAll());
    }

}

