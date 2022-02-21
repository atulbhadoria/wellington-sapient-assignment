package com.sapient.wellingtonsapientassignment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketValueRequest implements Serializable {
    @NotNull(message = "id must be present")
    private Integer id;

    private List<Integer> excludedHoldingIds;
}
