package org.example.advantshopfrontadintegration.dto.Frontpad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
@Data
public class FrontPadOrder {
    @JsonProperty("secret")
    @Value("${advantshop.secret}")
    public String secret;

    @JsonProperty("product")
    public List<String> product;

    @JsonProperty("product_kol")
    public List<String> product_kol;

    @JsonProperty("name")
    public String name;

    @JsonProperty("phone")
    public String phone;

    @JsonProperty("mail")
    public String mail;
    
    @JsonProperty("street")
    public String street;

    @JsonProperty("descr")
    public String descr;
}
