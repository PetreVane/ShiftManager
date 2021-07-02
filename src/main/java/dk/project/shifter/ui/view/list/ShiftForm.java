package dk.project.shifter.ui.view.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.exceptions.CustomException;

import java.time.Duration;
import java.time.LocalTime;

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

    public ShiftForm() {
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        startingTime.setMinTime(LocalTime.parse("05:00"));
        startingTime.setMaxTime(LocalTime.parse("22:00"));
        startingTime.setStep(Duration.ofMinutes(30));

        endingTime.setMinTime(LocalTime.parse("05:00"));
        endingTime.setMaxTime(LocalTime.parse("22:00"));
        endingTime.setStep(Duration.ofMinutes(30));

        add(shiftDate, startingTime, endingTime, hadBreak, configureHoursTypeCombo(), createButtonLayout());
    }

    public void setShift(Shift shift) {
        this.shift = shift;
        binder.readBean(shift);
    }


    private Component createButtonLayout() {
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

        binder.addStatusChangeListener(evt -> saveButton.setEnabled(binder.isValid()));

        buttonLayout.add(saveButton, cancelButton, deleteButton);
        return buttonLayout;
    }

    private void validateAndSave() throws CustomException {
        try {
            binder.writeBean(shift);
            fireEvent(new SaveEvent(this, shift));
        } catch (ValidationException e) {
            e.getMessage();
        }
    }

    private Component configureHoursTypeCombo() {
        var types = Shift.HoursType.values();
        hoursType.setItems(types);
        return hoursType;
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

