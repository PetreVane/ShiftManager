package dk.project.shifter.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import dk.project.shifter.ui.view.dashboard.DashboardView;
import dk.project.shifter.ui.view.list.ListView;

@CssImport("./styles/shared-styles.css")
@PWA(name = "ShifterApp", shortName = "Shifter", offlineResources = {"./styles/offline.css", "./images/offline.png"})
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Shift Recorder");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(listLink, new RouterLink("Dashboard", DashboardView.class)));
    }
}
