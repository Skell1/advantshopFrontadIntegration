package org.example.advantshopfrontadintegration.dto.AdvantShop;

import lombok.Data;

@Data
public class AdvantshoRequestBody {
    Integer Page;
    Integer ItemsPerPage;
    String DateFrom;
    String DateTo;
    Boolean LoadItems;

}
