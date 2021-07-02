package dk.project.shifter.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public CustomErrorResponse handleException(Exception exception) {
        return new CustomErrorResponse(exception.getMessage());
    }
}
