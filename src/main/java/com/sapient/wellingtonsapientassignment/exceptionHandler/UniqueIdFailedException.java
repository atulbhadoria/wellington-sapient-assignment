package com.sapient.wellingtonsapientassignment.exceptionHandler;

import com.sapient.wellingtonsapientassignment.model.Vertex;
import lombok.Data;

@Data
public class UniqueIdFailedException extends RuntimeException {
    private Vertex vertex;
    public UniqueIdFailedException(Vertex vertex) {
        this.vertex = vertex;
    }
}
