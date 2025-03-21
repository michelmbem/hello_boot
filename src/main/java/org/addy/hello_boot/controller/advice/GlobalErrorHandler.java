package org.addy.hello_boot.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {
    private static final String LOG_MSG_FMT = "An error of type {} occurred while requesting {} {}";
    private static final String ERROR_MSG_SUFFIX = ". Check the logs for more details";

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ApiErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        reportError(e);

        return ApiErrorResponse.of(ApiError.NOT_FOUND).toResponseEntity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        reportError(e);

        return ApiErrorResponse.of(ApiError.BAD_REQUEST).toResponseEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        reportError(e);

        return ApiErrorResponse.of(extractErrors(e)).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
        reportError(e);

        return ApiErrorResponse.of(ApiError.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

    private static Map<String, Object> extractErrors(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        List<String> globalErrors = allErrors.stream()
                .filter(error -> !(error instanceof FieldError))
                .map(ObjectError::getDefaultMessage)
                .toList();

        Map<String, Object> errors = allErrors.stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField,
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(),
                                String.format("value '%s' is rejected", error.getRejectedValue()))));

        if (!globalErrors.isEmpty()) {
            errors.put("*", globalErrors);
        }

        return errors;
    }

    private void reportError(Exception e) {
        HttpServletRequest currentRequest = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();

        String requestURI = ServletUriComponentsBuilder.fromRequest(currentRequest).build().toUriString();

        log.error(LOG_MSG_FMT, e.getClass().getSimpleName(), currentRequest.getMethod(), requestURI, e);
    }

    @RequiredArgsConstructor
    public enum ApiError {
        NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Not found", "We could not find the requested item"),
        CONFLICT(HttpStatus.CONFLICT.value(), "Conflict", "Another item exists with the same identifiers"),
        BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Bad request", "Data validation failed" + ERROR_MSG_SUFFIX),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "User authentication failed"),
        FORBIDDEN(HttpStatus.FORBIDDEN.value(), "Forbidden", "Access denied"),
        INTERNAL_SERVER_ERROR(HttpStatus.NOT_FOUND.value(), "Internal server error", "Something went wrong" + ERROR_MSG_SUFFIX);

        private final int statusCode;
        private final String statusText;
        private final String message;
    }

    @Data
    public static final class ApiErrorResponse {
        @Schema(description="HTTP status code", example="400")
        private final int statusCode;

        @Schema(description="HTTP status text", example="Bad request")
        private final String statusText;

        @Schema(description="Detailed error message", example="Data validation failed")
        private final String message;

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @Schema(description="In case of validation error, provides details on erroneous fields")
        private final Map<String, Object> errors;

        private ApiErrorResponse(int statusCode, String statusText, String message, Map<String, Object> errors) {
            this.statusCode = statusCode;
            this.statusText = statusText;
            this.message = message;
            this.errors = errors;
        }

        public static ApiErrorResponse of(ApiError apiError) {
            return new ApiErrorResponse(apiError.statusCode, apiError.statusText, apiError.message, null);
        }

        public static ApiErrorResponse of(Map<String, Object> errors) {
            return new ApiErrorResponse(
                    ApiError.BAD_REQUEST.statusCode,
                    ApiError.BAD_REQUEST.statusText,
                    ApiError.BAD_REQUEST.message,
                    errors
            );
        }

        public ResponseEntity<ApiErrorResponse> toResponseEntity() {
            return ResponseEntity.status(statusCode).body(this);
        }
    }
}
