package dk.project.shifter.ui;

import com.vaadin.flow.component.*;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import dk.project.shifter.backend.entity.Shift;

@Route("contactForm")
public class ShiftForm extends FormLayout {

    DatePicker shiftDate = new DatePicker("Shift date");
    TimePicker startingTime = new TimePicker("Shift started");
    TimePicker endingTime = new TimePicker("Shift ended");
    Checkbox hadBreak = new Checkbox("Had break");
    ComboBox<Shift.HoursType> hoursType = new ComboBox<>("Hours type");
    Button saveButton = new Button("Save");
    Button deleteButton = new Button("Delete");
    Button cancelButton = new Button("Cancel");

    Binder<Shift> binder = new BeanValidationBinder<>(Shift.class);
    private Shift shift;

    public void setShift(Shift shift) {
        this.shift = shift;
        binder.readBean(shift);
    }



    public <T> ShiftForm(SplitLayout splitLayout, Grid<T> grid) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        createEditorLayout(splitLayout);
        createGridLayout(splitLayout, grid);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        Component[] fields = new Component[]{shiftDate, startingTime, endingTime, hadBreak, configureHoursTypeCombo()};

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
        // theme
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // shortcuts
        saveButton.addClickShortcut(Key.ENTER);
        deleteButton.addClickShortcut(Key.DELETE);
        cancelButton.addClickShortcut(Key.ESCAPE);

        //events
        saveButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, shift)));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));



        buttonLayout.add(saveButton, cancelButton, deleteButton);
        editorLayoutDiv.add(buttonLayout);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(shift);
            fireEvent(new SaveEvent(this, shift));

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void createGridLayout(SplitLayout splitLayout, Grid grid) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private ComboBox<Shift.HoursType> configureHoursTypeCombo() {
        var types = Shift.HoursType.values();
        hoursType.setItems(types);
        return hoursType;
    }

    private void refreshGrid(Grid grid) {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }


    public static abstract class ShiftFormEvent extends ComponentEvent<ShiftForm> {

        private final Shift shift;

        protected ShiftFormEvent(ShiftForm source, Shift shift) {
            super(source, false);
            this.shift = shift;
        }

        public Shift getShift() {
            return shift;
        }
    }

    public static class SaveEvent extends ShiftFormEvent {
        SaveEvent(ShiftForm source, Shift shift) {
            super(source, shift);
        }
    }

    public static class DeleteEvent extends ShiftFormEvent {
        DeleteEvent(ShiftForm source, Shift shift) {
            super(source, shift);
        }
    }

    public static class CancelEvent extends ShiftFormEvent {
        public CancelEvent(ShiftForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
