package org.example.advantshopfrontadintegration.dto.Frontpad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class ResponseDTO {
    @JsonProperty("result")
    private String result;

    @JsonProperty("order_id")
    private Integer order_id;

    @JsonProperty("order_number")
    private Integer order_number;

    @JsonProperty("error")
    private String error;
}
