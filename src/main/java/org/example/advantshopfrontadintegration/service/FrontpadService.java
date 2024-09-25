package org.example.advantshopfrontadintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.example.advantshopfrontadintegration.dto.Frontpad.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class FrontpadService {

    @Value("${frontpad.uri}")
    private String advantshopUri;

    public ResponseDTO postNewOrder(FrontPadOrder frontPadOrder, Integer frontPadOrderId) {
        final String uri = advantshopUri + "?new_order";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(frontPadOrder, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResponseDTO> response = restTemplate.exchange(uri,
                HttpMethod.POST,
                entity,
                ResponseDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Ошибка получения списка заказов");
            return null;
        } else {
            if (!Objects.requireNonNull(response.getBody()).getResult().equals("success")) {
                log.error("Ошибка получения списка заказов error- {}", Objects.requireNonNull(response.getBody()).getError());
            }
            ResponseDTO responseDTO = Objects.requireNonNull(response.getBody());
            log.info("Заказ orderNumber = {} Передан в FrontPad c order_number = {}", frontPadOrderId, responseDTO.getOrderNumber());
            return responseDTO;
        }
    }
}
