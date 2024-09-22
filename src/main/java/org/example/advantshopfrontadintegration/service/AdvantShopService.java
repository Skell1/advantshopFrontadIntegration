package org.example.advantshopfrontadintegration.service;


import org.example.advantshopfrontadintegration.dto.AdvantShop.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class AdvantShopService {

    private static final Logger log = LoggerFactory.getLogger(AdvantShopService.class);
    @Value("${advantshop.uri}")
    private String advantshopUri;

    @Value("${advantshop.apiKey}")
    private String apiKey;

    public OrdersDTO getOrderList() {
        final String uri = advantshopUri + "api/order/getlist";
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri).queryParam("apiKey", apiKey).encode().toUriString();
        AdvantshoRequestBody body = new AdvantshoRequestBody();
        body.setDateFrom(LocalDate.now().minusDays(7).atStartOfDay().toString());
        //body.setDateTo(LocalDate.now().plusDays(1).atStartOfDay().toString());

        body.setPage(1);
        body.setItemsPerPage(100);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResponseListOrdersDTO> response = restTemplate.exchange(urlTemplate,
                HttpMethod.POST,
                entity,
                ResponseListOrdersDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Ошибка получения списка заказов error- {}", Objects.requireNonNull(response.getBody()).getErrors());
            return null;
        } else {
            if (!response.getBody().isResult() || Objects.nonNull(response.getBody().getErrors())) {
                log.error("Ошибка получения списка заказов error- {}", Objects.requireNonNull(response.getBody().getErrors()));
                return null;
            }
            OrdersDTO ordersDTO = (OrdersDTO) Objects.requireNonNull(response.getBody()).getObj();
            return ordersDTO;
        }
    }

    public DataItemDTO getOrderById(Integer orderId) {
        final String uri = advantshopUri + "api/order/get/" + orderId;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri).queryParam("apiKey", apiKey).encode().toUriString();
        AdvantshoRequestBody body = new AdvantshoRequestBody();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResponseOrderDTO> response = restTemplate.exchange(urlTemplate,
                HttpMethod.GET,
                entity,
                ResponseOrderDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Ошибка получения заказ error- {}", Objects.requireNonNull(response.getBody()).getErrors());
            return null;
        } else {
            if (!response.getBody().isResult() || Objects.nonNull(response.getBody().getErrors())) {
                log.error("Ошибка получения заказа error- {}", Objects.requireNonNull(response.getBody().getErrors()));
                return null;
            }
            DataItemDTO dataItemDTO = Objects.requireNonNull(response.getBody()).getObj();
            return dataItemDTO;
        }
    }
}
