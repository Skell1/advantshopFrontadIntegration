package org.example.advantshopfrontadintegration.converter;

import org.example.advantshopfrontadintegration.dto.AdvantShop.CustomerDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.DataItemDTO;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class FrontPadOrderConverter {

    public FrontPadOrder convert(DataItemDTO dataItemDTO) {
        FrontPadOrder frontPadOrder = new FrontPadOrder();
        CustomerDTO customerDTO = dataItemDTO.getCustomer();
        frontPadOrder.name = customerDTO.getLastName() + " " + customerDTO.getFirstName() + " " + customerDTO.getPatronymic();
        frontPadOrder.phone = customerDTO.getPhone();
        frontPadOrder.product = null;
        frontPadOrder.product_kol = null;
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


