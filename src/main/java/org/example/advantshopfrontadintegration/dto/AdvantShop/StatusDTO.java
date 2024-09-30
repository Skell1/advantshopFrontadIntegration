package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatusDTO {
    @JsonProperty("Id")
    private int id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Color")
    private String color;

    @JsonProperty("IsCanceled")
    private boolean isCanceled;

    @JsonProperty("IsCompleted")
    private boolean isCompleted;

    @JsonProperty("Hidden")
    private boolean hidden;

    @JsonProperty("IsCancellationForbidden")
    private boolean isCancellationForbidden;

    
    
}
