package com.sapient.wellingtonsapientassignment.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class InvestorNotFoundException extends RuntimeException{
   private String message;
}
