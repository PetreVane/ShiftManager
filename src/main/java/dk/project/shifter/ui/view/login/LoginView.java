package dk.project.shifter.ui.view.login;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm loginForm = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");
        var credentials = new Text("Use 'user' to log in and 'test123' as password!");
        add(new H1("Shifter app"), credentials, loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
            if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
                loginForm.setError(true);
            }
    }
}
