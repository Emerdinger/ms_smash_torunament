package com.emerdinger.smashtorunament.helpers.errors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
@Log4j2
public class ErrorHandler {

    private static final int UNEXPECTED_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error";
    private static final ConcurrentHashMap<String, Integer> STATUS_CODES = new ConcurrentHashMap<>();

    public ErrorHandler() {
        STATUS_CODES.put(NotFoundError.class.getSimpleName(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<TournamentError> handleAllExceptions(Exception exception) {
        ResponseEntity<TournamentError> errorResult;

        String exceptionName = exception.getClass().getSimpleName();
        String msg = exception.getMessage();
        Integer code = STATUS_CODES.get(exceptionName);

        log.error("{} {}", exceptionName, msg);

        if (code != null) {
            TournamentError error = new TournamentError(exceptionName, msg, code);
            errorResult = new ResponseEntity<>(error, HttpStatusCode.valueOf(code));
        } else {
            TournamentError error = new TournamentError(exceptionName, UNEXPECTED_ERROR_MESSAGE, UNEXPECTED_ERROR_CODE);
            errorResult = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return errorResult;
    }

}
