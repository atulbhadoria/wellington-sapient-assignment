package com.sapient.wellingtonsapientassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fund implements Vertex {
    @NotNull(message = "Id for Fund must be present")
    private Integer id;

    @NotBlank(message = "Name for Fund must be present")
    private String name;
}
