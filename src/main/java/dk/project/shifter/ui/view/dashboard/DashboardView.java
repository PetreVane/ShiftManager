package dk.project.shifter.ui.view.dashboard;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dk.project.shifter.backend.service.ShiftService;
import dk.project.shifter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
public class DashboardView extends VerticalLayout {

        private ShiftService shiftService;

        public DashboardView (@Autowired ShiftService shiftService) {
                this.shiftService = shiftService;
                addClassName("dashboard-view");
                add(getShiftStats(), getShiftsByType());
                add(new SummaryView(shiftService));
        }

        private Component getShiftStats() {
                Span stats = new Span(" You have in total " + shiftService.countShifts() + " entries in your database");
                addClassName("shift-stats");
                return stats;
        }

        private Chart getShiftsByType() {
                Chart chart = new Chart(ChartType.PIE);
                DataSeries dataSeries = new DataSeries();
                var mappedShifts = shiftService.mapShiftsByType();
                mappedShifts.forEach((shiftType, counter) ->
                        dataSeries.add(new DataSeriesItem(shiftType, counter)));
                chart.getConfiguration().setSeries(dataSeries);
                return chart;
        }

}
