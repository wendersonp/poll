package com.personal.poll.api.handler;

import com.personal.poll.domain.exception.ClosedPollException;
import com.personal.poll.domain.exception.MemberCpfNotAllowedException;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.exception.MemberCpfNotValidException;
import com.personal.poll.domain.exception.PendingPollException;
import com.personal.poll.domain.exception.VoteAlreadyRegisteredException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MemberCpfNotUniqueException.class, VoteAlreadyRegisteredException.class})
    public ResponseEntity<Object> handleConflictException(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({ClosedPollException.class, PendingPollException.class, MemberCpfNotValidException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MemberCpfNotAllowedException.class})
    public ResponseEntity<Object> handleForbiddenException(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.FORBIDDEN, ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
