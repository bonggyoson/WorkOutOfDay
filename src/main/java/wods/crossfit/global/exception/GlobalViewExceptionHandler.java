package wods.crossfit.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.webjars.NotFoundException;
import wods.crossfit.global.common.CommonResponse;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class GlobalViewExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> serverErrorHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.res(HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundExceptionHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.res(HttpStatus.NOT_FOUND, e.getMessage()));
    }

}
