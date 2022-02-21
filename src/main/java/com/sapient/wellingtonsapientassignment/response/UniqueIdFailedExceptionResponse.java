package com.sapient.wellingtonsapientassignment.response;

import com.sapient.wellingtonsapientassignment.model.Vertex;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UniqueIdFailedExceptionResponse {
    private String message;
    private LocalDateTime dateTime;
    private Vertex vertex;
}
