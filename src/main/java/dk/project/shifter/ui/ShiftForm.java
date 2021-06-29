package dk.project.shifter.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Shift;

@Route("contactForm")
public class ShiftForm extends FormLayout {

    DatePicker datePicker = new DatePicker("Shift date");
    TimePicker startingTimePicker = new TimePicker("Shift started");
    TimePicker endingTimePicker = new TimePicker("Shift ended");
    Checkbox breakCheckbox = new Checkbox("Had break");
    ComboBox<Shift.HoursType> hoursTypeComboBox = new ComboBox<>("Hours type");

    Button saveButton = new Button("Save");
    Button cancelButton = new Button("Cancel");


    public <T> ShiftForm(SplitLayout splitLayout, Grid<T> grid) {
        createEditorLayout(splitLayout);
        createGridLayout(splitLayout, grid);
    }

    private HorizontalLayout createButtonsLayout() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(saveButton, cancelButton);
    }

    private ComboBox<Shift.HoursType> configureComboBox() {
        Shift.HoursType[] types = Shift.HoursType.values();
        hoursTypeComboBox.setItems(types);
        return hoursTypeComboBox;
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        Component[] fields = new Component[]{datePicker, startingTimePicker, endingTimePicker, breakCheckbox, configureComboBox()};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(saveButton, cancelButton);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout, Grid grid) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid(Grid grid) {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }
}
