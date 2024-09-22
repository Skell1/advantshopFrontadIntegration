package org.example.advantshopfrontadintegration.dto.AdvantShop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    @JsonProperty("CustomerId")
    private String customerId;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("Patronymic")
    private String patronymic;

    @JsonProperty("Organization")
    private String organization;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("District")
    private String district;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Zip")
    private String zip;

    @JsonProperty("CustomField1")
    private String customField1;

    @JsonProperty("CustomField2")
    private String customField2;

    @JsonProperty("CustomField3")
    private String customField3;

    @JsonProperty("Street")
    private String street;

    @JsonProperty("House")
    private String house;

    @JsonProperty("Apartment")
    private String apartment;

    @JsonProperty("Structure")
    private String structure;

    @JsonProperty("Entrance")
    private String entrance;

    @JsonProperty("Floor")
    private String floor;

    // Геттеры и сеттеры
    // ...
}
