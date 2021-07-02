package dk.project.shifter.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.service.ShiftService;
import dk.project.shifter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Listing of your shifts")
public class ListView extends VerticalLayout {

    private final Grid<Shift> grid = new Grid<>(Shift.class);
    private final ShiftService shiftService;
    private ShiftForm shiftForm;


    public ListView(@Autowired ShiftService shiftService) {
        this.shiftService = shiftService;
        configureUIElements();
    }

    private void configureUIElements() {
        addClassName("list-view"); // mainView css styling
        setSizeFull(); // applies to the mainView
        configureGrid();

        shiftForm = new ShiftForm();
        shiftForm.setVisible(false);
        Div content = new Div(grid, shiftForm);
        content.addClassName("content");
        content.setSizeFull();

        shiftForm.addListener(ShiftForm.SaveEvent.class, this::saveShift);
        shiftForm.addListener(ShiftForm.DeleteEvent.class, this::deleteShift);
        shiftForm.addListener(ShiftForm.CancelEvent.class, e -> this.clearEditor());

        updateGridContent();
        add(getToolbar(), content); // getToolbar()
    }

    private HorizontalLayout getToolbar() {
        Button addShiftButton = new Button("Add new shift");
        addShiftButton.addClickListener(click -> addShift());
        HorizontalLayout toolBar = new HorizontalLayout(addShiftButton);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    // grid
    private void configureGrid() {
        grid.addClassName("contact-grid"); // grid css styling
        grid.setSizeFull();
        grid.setColumns("shiftDate", "startingTime", "endingTime", "hadBreak", "hoursType");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        // shows selected row
        grid.asSingleSelect().addValueChangeListener(event -> editShift(event.getValue()));
    }

    private void updateGridContent() {
        grid.setItems(shiftService.findAll());
    }

    // editor
    private void clearEditor() {
        shiftForm.setShift(null);
        shiftForm.setVisible(false);
        shiftForm.removeClassName("editing");
    }

    public void addShift() {
        grid.asSingleSelect().clear();
        Shift shift = new Shift();
        editShift(shift);
    }

    private void editShift(Shift shift) {
        if (shift == null) {
            clearEditor();
        } else {
            shiftForm.setVisible(true);
            shiftForm.setShift(shift);
            addClassName("editing");
        }
    }

    private void saveShift(ShiftForm.ShiftFormEvent event) {
        shiftService.saveShift(event.getShift());
        updateGridContent();
        clearEditor();
    }

    private void deleteShift(ShiftForm.ShiftFormEvent event) {
        Shift shiftToBeDeleted = event.getShift();
        if (shiftToBeDeleted == null) {
            shiftForm.setVisible(false);
        } else  {
            shiftService.deleteShift(shiftToBeDeleted);
            updateGridContent();
            clearEditor();
        }
    }
}

