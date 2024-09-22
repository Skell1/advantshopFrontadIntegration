package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataItemDTO {

    @JsonProperty("Id")
    private int id;

    @JsonProperty("Number")
    private int number;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Sum")
    private double sum;

    @JsonProperty("Date")
    private String date; // вы можете изменить на LocalDateTime в зависимости от ваших нужд

    @JsonProperty("CustomerComment")
    private String customerComment;

    @JsonProperty("AdminComment")
    private String adminComment;

    @JsonProperty("PaymentName")
    private String paymentName;

    @JsonProperty("PaymentCost")
    private double paymentCost;

    @JsonProperty("ShippingName")
    private String shippingName;

    @JsonProperty("ShippingCost")
    private double shippingCost;

    @JsonProperty("ShippingTaxName")
    private String shippingTaxName;

    @JsonProperty("TrackNumber")
    private String trackNumber;

    @JsonProperty("DeliveryDate")
    private String deliveryDate; // вы можете изменить на LocalDateTime в зависимости от ваших нужд

    @JsonProperty("DeliveryTime")
    private String deliveryTime;

    @JsonProperty("OrderDiscount")
    private double orderDiscount;

    @JsonProperty("OrderDiscountValue")
    private double orderDiscountValue;

    @JsonProperty("BonusCardNumber")
    private String bonusCardNumber;

    @JsonProperty("BonusCost")
    private double bonusCost;

    @JsonProperty("LpId")
    private String lpId;

    @JsonProperty("IsPaid")
    private boolean isPaid;

    @JsonProperty("PaymentDate")
    private String paymentDate; // вы можете изменить на LocalDateTime в зависимости от ваших нужд

    @JsonProperty("Customer")
    private CustomerDTO customer;

    @JsonProperty("Status")
    private StatusDTO status;

    @JsonProperty("Source")
    private SourceDTO source;

    @JsonProperty("ModifiedDate")
    private String modifiedDate; // вы можете изменить на LocalDateTime в зависимости от ваших нужд
}
