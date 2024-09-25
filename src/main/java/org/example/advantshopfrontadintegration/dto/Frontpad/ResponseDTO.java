package org.example.advantshopfrontadintegration.dto.Frontpad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDTO {
    @JsonProperty("result")
    private String result;

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("order_number")
    private Integer orderNumber;

    @JsonProperty("error")
    private String error;
}
