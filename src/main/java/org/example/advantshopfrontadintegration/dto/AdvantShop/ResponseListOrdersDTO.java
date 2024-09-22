package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListOrdersDTO {

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("obj")
    private OrdersDTO obj;

    @JsonProperty("errors")
    private List<String> errors;
}

