package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDTO {

    @JsonProperty("DataItems")
    private List<DataItemDTO> dataItems;

    @JsonProperty("TotalPageCount")
    private int totalPageCount;

    @JsonProperty("TotalItemsCount")
    private int totalItemsCount;

    @JsonProperty("PageIndex")
    private int pageIndex;

    @JsonProperty("ItemsPerPage")
    private int itemsPerPage;

}
