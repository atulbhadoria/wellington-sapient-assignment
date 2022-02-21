package com.sapient.wellingtonsapientassignment.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundNotFoundException extends RuntimeException {
    private String message;
}
