package com.services.repo.advice;


import com.services.repo.exception.InvalidOwnerException;
import com.services.repo.exception.InvalidRepositoryNameException;
import com.services.repo.exception.RepoNotFoundException;
import com.services.repo.model.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidOwnerException.class, InvalidRepositoryNameException.class, RepoNotFoundException.class})
    public ResponseEntity<Object> handleBadRequest(Exception exception, HttpServletRequest request, HttpServletResponse response) {

        log.error("Encountered error ", exception);

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(status.value())
                .timestamp(new Date().toString())
                .errors(Arrays.asList(exception.getMessage()))
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception exception, HttpServletRequest request, HttpServletResponse response) {

        log.error("Encountered error ", exception);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(status.value())
                .timestamp(new Date().toString())
                .errors(Arrays.asList(exception.getMessage()))
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("Error validating request ", exception);
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(status.value())
                .timestamp(new Date().toString())
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }
}
