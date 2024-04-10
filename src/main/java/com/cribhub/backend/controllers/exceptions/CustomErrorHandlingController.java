package com.cribhub.backend.controllers.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Log4j2
class CustomErrorHandlingController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    CustomErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        e.getConstraintViolations().forEach(violation -> error.getErrors().add(
                new CustomError(violation.getPropertyPath().toString(), violation.getMessage())));
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    CustomErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> error.getErrors().add(
                new CustomError(fieldError.getField(), fieldError.getDefaultMessage())));
        return error;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    CustomErrorResponse onCustomerNotFoundException(CustomerNotFoundException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.getErrors().add(
                new CustomError("Customer not found", e.getMessage()));

        log.error("Customer not found: {}", e.getMessage());
        return error;
    }

    @ExceptionHandler(CribNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    CustomErrorResponse onCribNotFoundException(CribNotFoundException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.getErrors().add(
                new CustomError("Crib not found", e.getMessage()));

        log.error("Crib not found: {}", e.getMessage());
        return error;
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    CustomErrorResponse onEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.getErrors().add(
                new CustomError("Email", e.getMessage()));

        log.error("Email already in use: {}", e.getMessage());
        return error;
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    CustomErrorResponse onUsernameAlreadyTakenException(UsernameAlreadyTakenException e) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.getErrors().add(
                new CustomError("Username", e.getMessage()));

        log.error("Username already taken: {}", e.getMessage());
        return error;
    }
}
