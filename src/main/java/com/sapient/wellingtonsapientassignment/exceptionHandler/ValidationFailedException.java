package com.sapient.wellingtonsapientassignment.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationFailedException extends RuntimeException{
    private String message;
}
