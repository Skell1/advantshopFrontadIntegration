package org.example.advantshopfrontadintegration.converter;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.dto.AdvantShop.CustomerDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.DataItemDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.Item;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Component
@Slf4j
public class FrontPadOrderConverter {

    @Value("${frontpad.secret}")
    private String secret;

    public FrontPadOrder convert(DataItemDTO dataItemDTO) {
        FrontPadOrder frontPadOrder = new FrontPadOrder();
        CustomerDTO customerDTO = dataItemDTO.getCustomer();
        frontPadOrder.secret = secret;
        frontPadOrder.name = customerDTO.getLastName() + " " + customerDTO.getFirstName() + " " + customerDTO.getPatronymic();
        frontPadOrder.phone = customerDTO.getPhone();

        List<String> products = new ArrayList<>();
        List<String> productKols = new ArrayList<>();
        for (Item item: dataItemDTO.getItems()) {
            if (Objects.nonNull(item.getArtNo()) && Objects.nonNull(item.getAmount())) {
                products.add(item.getArtNo());
                productKols.add(item.getAmount());
            } else log.error("Заказ - {} артикул или количество не задано", dataItemDTO.getId());
        }
        frontPadOrder.product = products;
        frontPadOrder.product_kol = productKols;

        frontPadOrder.mail = customerDTO.getEmail();

        StringBuilder addressBuilder = new StringBuilder();
        if (Objects.nonNull(customerDTO.getCity())) addressBuilder.append("Город ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getStreet())) addressBuilder.append("Улица ").append(customerDTO.getStreet()).append(", ");
        if (Objects.nonNull(customerDTO.getHouse())) addressBuilder.append("Дом ").append(customerDTO.getHouse()).append(", ");
        if (Objects.nonNull(customerDTO.getStructure())) addressBuilder.append("Стр./корп. ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getApartment())) addressBuilder.append("Квартира ").append(customerDTO.getCity()).append(", ");
        frontPadOrder.street = addressBuilder.toString();

        frontPadOrder.descr = "Заказ " + dataItemDTO.getId() + ". " + dataItemDTO.getCustomerComment();

        return frontPadOrder;
    }
}


