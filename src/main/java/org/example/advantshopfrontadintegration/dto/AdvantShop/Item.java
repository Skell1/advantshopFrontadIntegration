package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {
    @JsonProperty("ArtNo")
    private String artNo;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Color")
    private String color;

    @JsonProperty("Size")
    private String size;

    @JsonProperty("Price")
    private String price;

    @JsonProperty("Amount")
    private String amount;

    @JsonProperty("PhotoSrc")
    private String photoSrc;

}
