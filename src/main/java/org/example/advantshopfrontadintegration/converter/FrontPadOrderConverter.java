package org.example.advantshopfrontadintegration.converter;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.dto.AdvantShop.CustomerDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.DataItemDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.Item;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

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

    public Map<String, String> convertToMap(DataItemDTO dataItemDTO) {
        Map<String, String> maps = new HashMap<>();

        CustomerDTO customerDTO = dataItemDTO.getCustomer();
        maps.put("secret", secret);
        maps.put("name", customerDTO.getLastName() + " " + customerDTO.getFirstName() + " " + customerDTO.getPatronymic());
        maps.put("phone", customerDTO.getPhone());

        for (Item item: dataItemDTO.getItems()) {
            if (Objects.nonNull(item.getArtNo()) && Objects.nonNull(item.getAmount())) {
                maps.put("product", item.getArtNo());
                maps.put("product_kol", Integer.toString(Integer.parseInt(item.getAmount())));
            } else log.error("Заказ - {} артикул или количество не задано", dataItemDTO.getId());
        }

        maps.put("product", "1");
        maps.put("product_kol", "1");
        maps.put("product", "2");
        maps.put("product_kol", "2");

        maps.put("mail", customerDTO.getEmail());

        StringBuilder addressBuilder = new StringBuilder();
        if (Objects.nonNull(customerDTO.getCity())) addressBuilder.append("Город ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getStreet())) addressBuilder.append("Улица ").append(customerDTO.getStreet()).append(", ");
        if (Objects.nonNull(customerDTO.getHouse())) addressBuilder.append("Дом ").append(customerDTO.getHouse()).append(", ");
        if (Objects.nonNull(customerDTO.getStructure())) addressBuilder.append("Стр./корп. ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getApartment())) addressBuilder.append("Квартира ").append(customerDTO.getCity()).append(", ");
        maps.put("street", addressBuilder.toString());

        maps.put("descr", "Заказ " + dataItemDTO.getId() + ". " + dataItemDTO.getCustomerComment());

        return maps;
    }

    public MultiValueMap<String, String> convertToMultiValueMap(DataItemDTO dataItemDTO) {
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();

        CustomerDTO customerDTO = dataItemDTO.getCustomer();
        maps.put("secret", Collections.singletonList(secret));
        maps.put("name", Collections.singletonList(customerDTO.getLastName() + " " + customerDTO.getFirstName() + " " + customerDTO.getPatronymic()));
        maps.put("phone", Collections.singletonList(customerDTO.getPhone()));


        for (int i = 0; i < dataItemDTO.getItems().size(); i++) {
            if (Objects.nonNull(dataItemDTO.getItems().get(i).getArtNo()) && Objects.nonNull(dataItemDTO.getItems().get(i).getAmount())) {
                maps.put("product[" + i + "]", Collections.singletonList(dataItemDTO.getItems().get(i).getArtNo()));
                maps.put("product_kol[" + i + "]", Collections.singletonList(dataItemDTO.getItems().get(i).getAmount()));
            } else log.error("Заказ - {} артикул или количество не задано", dataItemDTO.getId());
        }

        maps.put("mail", Collections.singletonList(customerDTO.getEmail()));

        StringBuilder addressBuilder = new StringBuilder();
        if (Objects.nonNull(customerDTO.getCity())) addressBuilder.append("Город ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getStreet())) addressBuilder.append("Улица ").append(customerDTO.getStreet()).append(", ");
        if (Objects.nonNull(customerDTO.getHouse())) addressBuilder.append("Дом ").append(customerDTO.getHouse()).append(", ");
        if (Objects.nonNull(customerDTO.getStructure())) addressBuilder.append("Стр./корп. ").append(customerDTO.getCity()).append(", ");
        if (Objects.nonNull(customerDTO.getApartment())) addressBuilder.append("Квартира ").append(customerDTO.getCity()).append(", ");
        maps.put("street", Collections.singletonList(addressBuilder.toString()));

        maps.put("descr", Collections.singletonList("Заказ " + dataItemDTO.getId() + ". " + dataItemDTO.getCustomerComment()));

        return maps;
    }
}


