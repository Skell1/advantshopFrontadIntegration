package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseOrderDTO {

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("obj")
    private DataItemDTO obj;

    @JsonProperty("errors")
    private List<String> errors;
}

