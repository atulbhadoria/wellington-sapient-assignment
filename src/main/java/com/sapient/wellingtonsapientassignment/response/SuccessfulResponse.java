package com.sapient.wellingtonsapientassignment.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SuccessfulResponse {
    private Integer marketValue;
    private LocalDateTime dateTime;
}
