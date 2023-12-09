package com.personal.poll.api.handler;

import com.personal.poll.domain.exception.ClosedPollException;
import com.personal.poll.domain.exception.MemberCpfNotAllowedException;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.exception.MemberCpfNotValidException;
import com.personal.poll.domain.exception.PendingPollException;
import com.personal.poll.domain.exception.PollNotPendingException;
import com.personal.poll.domain.exception.VoteAlreadyRegisteredException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MemberCpfNotUniqueException.class, VoteAlreadyRegisteredException.class})
    public ResponseEntity<ErrorDetail> handleConflictException(RuntimeException ex, HttpServletRequest request) {
       return buildResponse(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler({ClosedPollException.class,
            PendingPollException.class,
            PollNotPendingException.class,
            MemberCpfNotValidException.class,
    })
    public ResponseEntity<ErrorDetail> handleBadRequestException(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler({MemberCpfNotAllowedException.class})
    public ResponseEntity<ErrorDetail> handleForbiddenException(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, request, ex);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorDetail> handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, request, ex);

    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDetail> handleInternalServerError(RuntimeException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }



    private ResponseEntity<ErrorDetail> buildResponse(HttpStatus status, HttpServletRequest request, Exception ex) {
        return ResponseEntity.status(status).body(ErrorDetail.builder()
                .statusCode(status.value())
                .path(request.getPathInfo())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            HttpStatusCode status,
            @NotNull WebRequest request) {
        var errors = getValidationErrors(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDetail.builder()
                .statusCode(status.value())
                .path(null)
                .errors(errors)
                .message(ex.getBody().getTitle())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    private Map<String, String> getValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(field, errorMessage);
        });
        return errors;
    }
}
