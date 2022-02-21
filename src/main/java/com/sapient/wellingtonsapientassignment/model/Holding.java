package com.sapient.wellingtonsapientassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holding implements Vertex {
    @NotNull(message = "Id for Holding must be present")
    private Integer id;
    @NotBlank(message = "Name for Holding must be present")
    private String name;
    @NotNull(message = "Amount for Holding must be present")
    private Integer amount;
}
