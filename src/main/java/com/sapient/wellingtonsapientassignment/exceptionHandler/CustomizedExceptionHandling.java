package com.sapient.wellingtonsapientassignment.exceptionHandler;

import com.sapient.wellingtonsapientassignment.response.ExceptionResponse;
import com.sapient.wellingtonsapientassignment.response.UniqueIdFailedExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedExceptionHandling {
    @ExceptionHandler(InvestorNotFoundException.class)
    public ResponseEntity<Object> handleInvestorNotFoundExceptions(InvestorNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(FundNotFoundException.class)
    public ResponseEntity<Object> handleFundNotFoundExceptions(FundNotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Object> handleValidationFailedExceptions(ValidationFailedException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(UniqueIdFailedException.class)
    public ResponseEntity<Object> handleIdNotUniqueExceptions(UniqueIdFailedException exception, WebRequest webRequest) {
        UniqueIdFailedExceptionResponse response = new UniqueIdFailedExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Provided vertex doesn't have unique id");
        response.setVertex(exception.getVertex());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleTest(MethodArgumentNotValidException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getFieldErrors().stream().map(m -> m.getDefaultMessage()).reduce((a,b) -> a+","+b).orElse(""));
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getLocalizedMessage());

        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }
}
