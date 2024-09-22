package org.example.advantshopfrontadintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.dto.AdvantShop.OrdersDTO;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FrontpadService {

    @Value("${frontpad.uri}")
    private String advantshopUri;

    @Value("${frontpad.secret}")
    private String secret;

    public OrdersDTO postNewOrder(FrontPadOrder frontPadOrder) {
        final String uri = advantshopUri + "?get_client";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(frontPadOrder, headers);
        RestTemplate restTemplate = new RestTemplate();

//        ResponseEntity<ResponseDTO> response = restTemplate.exchange(uri,
//                HttpMethod.POST,
//                entity,
//                ResponseDTO.class);
//        if (response.getStatusCode() != HttpStatus.OK) {
//            log.error("Ошибка получения списка заказов error- {}", Objects.requireNonNull(response.getBody()).getErrors());
//            return null;
//        } else {
//            if (!response.getBody().isResult()) {
//                log.error("Ошибка получения списка заказов error- {}", Objects.requireNonNull(response.getBody()).getErrors());
//            }
//            OrdersDTO ordersDTO = (OrdersDTO) Objects.requireNonNull(response.getBody()).getObj();
//            return ordersDTO;
//        }
        return null;
    }
}
