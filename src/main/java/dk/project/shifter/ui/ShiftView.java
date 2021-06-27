package dk.project.shifter.ui;

//import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.entity.Shift;
import dk.project.shifter.backend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Optional;

@Route(value = "shifts")
@PageTitle("Shifts")
public class ShiftView extends Div implements BeforeEnterObserver {

    private final String SHIFT_ID = "shiftID";
    private final String SHIFT_EDIT_ROUTE_TEMPLATE = "empty/%d/edit";

    private Grid<Shift> grid = new Grid<>(Shift.class, false);

    private DatePicker shiftDate;
    private TimePicker startingTime;
    private TimePicker endingTime;
    private Checkbox hadBreak;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Shift> binder;

    private Shift shift;

    private ShiftService shiftService;

    public ShiftView(@Autowired ShiftService shiftService) {
        try { getShiftsID(); }
        catch (Exception e) { System.out.println(e.getMessage()); }


        addClassNames("shifts-view", "flex", "flex-col", "h-full");
        this.shiftService = shiftService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("shiftDate").setAutoWidth(true);
        grid.addColumn("startingTime").setAutoWidth(true);
        grid.addColumn("endingTime").setAutoWidth(true);
        TemplateRenderer<Shift> hadBreakRenderer = TemplateRenderer.<Shift>of(
                "<iron-icon hidden='[[!item.hadBreak]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></iron-icon><iron-icon hidden='[[item.hadBreak]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></iron-icon>")
                .withProperty("hadBreak", Shift::isHadBreak);
        grid.addColumn(hadBreakRenderer).setHeader("Had Break").setAutoWidth(true);

        // TODO: fix datasource
//        grid.setDataProvider((DataProvider<Shift, ?>) shiftService.findAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SHIFT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ShiftView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Shift.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.shift == null) {
                    this.shift = new Shift();
                }
                binder.writeBean(this.shift);

                shiftService.saveShift(this.shift);
                clearForm();
                refreshGrid();
                Notification.show("Shift details stored.");
                UI.getCurrent().navigate(ShiftView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the shift details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> shiftId = event.getRouteParameters().getInteger(SHIFT_ID);
        if (shiftId.isPresent()) {
            Optional<Shift> shiftFromBackend = Optional.ofNullable(shiftService.getShift(shiftId.get().longValue())); //shiftService.get(shiftId.get());
            if (shiftFromBackend.isPresent()) {
                populateForm(shiftFromBackend.get());
            } else {
                Notification.show(String.format("The requested shift was not found, ID = %d", shiftId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ShiftView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        shiftDate = new DatePicker("Shift Date");
        startingTime = new TimePicker("Starting Time");
        startingTime.setStep(Duration.ofSeconds(1));
        endingTime = new TimePicker("Ending Time");
        endingTime.setStep(Duration.ofSeconds(1));
        hadBreak = new Checkbox("Had Break");
        hadBreak.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{shiftDate, startingTime, endingTime, hadBreak};

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
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Shift value) {
        this.shift = value;
        binder.readBean(this.shift);

    }

    private void getShiftsID() throws Exception {
        if (shiftService != null) {
            var shifts = shiftService.findAll();
            for (Shift shift : shifts) {
                System.out.println("This shift has id " + shift.getId());
            }
        } else {
            throw new Exception("ShiftService not injected into ShiftView");
        }
    }

}
