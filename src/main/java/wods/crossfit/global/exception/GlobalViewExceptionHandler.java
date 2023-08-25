package wods.crossfit.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.webjars.NotFoundException;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class GlobalViewExceptionHandler {

    @ExceptionHandler(InternalServerError.class)
    public String handleViewException(InternalServerError e, Model model) {

        model.addAttribute("error", e.getMessage());

        return "error/500";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleViewException(IllegalArgumentException e, Model model) {

        model.addAttribute("error", e.getMessage());

        return "error/500";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleViewException(NotFoundException e, Model model) {

        model.addAttribute("result", e.getMessage());

        return "login/resetPassword";
    }

}
