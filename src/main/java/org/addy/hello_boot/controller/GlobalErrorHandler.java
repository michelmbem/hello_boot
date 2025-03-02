package org.addy.hello_boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {
    private static final String LOG_MSG_FMT = "An error of type {} occurred while requesting {} {}";
    private static final String SERVER_ERROR_MSG = "Something went wrong. Consult the log for more details";

    @ExceptionHandler({NoSuchElementException.class})
    ResponseEntity<Void> noSuchElementHandler(NoSuchElementException e) {
        reportError(e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> validationHandler(MethodArgumentNotValidException e) {
        reportError(e);

        Map<String, String> errors = e.getBindingResult().getAllErrors().stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField,
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(), "")));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({Exception.class})
    ResponseEntity<String> defaultHandler(Exception e) {
        reportError(e);
        return ResponseEntity.status(500).body(SERVER_ERROR_MSG);
    }

    private void reportError(Exception e) {
        try {
            String requestMethod = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest()
                    .getMethod();

            String fullRequestURL = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri()
                    .toURL()
                    .toExternalForm();

            log.error(LOG_MSG_FMT, e.getClass().getSimpleName(), requestMethod, fullRequestURL, e);
        } catch (MalformedURLException e1) {
            log.error("Something went wrong", e);
        }
    }
}
