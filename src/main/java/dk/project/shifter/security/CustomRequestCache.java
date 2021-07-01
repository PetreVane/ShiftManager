package dk.project.shifter.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomRequestCache extends HttpSessionRequestCache {

//    Saves unauthenticated requests so the user can be redirected to the page
//    he was trying to access once he was logged in.
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }
}
