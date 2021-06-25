package dk.project.shifter.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

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

    public MainView() {
        var text = new Text("This is an example");
        var button = new Button("Please click me");

        HorizontalLayout layout = new HorizontalLayout(text, button);
        layout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        add(layout);
    }

}
