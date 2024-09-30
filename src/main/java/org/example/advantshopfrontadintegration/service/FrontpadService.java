package org.example.advantshopfrontadintegration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.example.advantshopfrontadintegration.dto.Frontpad.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class FrontpadService {

    @Value("${frontpad.uri}")
    private String advantshopUri;

    private final ObjectMapper objectMapper;

    public FrontpadService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public ResponseDTO postNewOrder(FrontPadOrder frontPadOrder, Integer frontPadOrderId, MultiValueMap<String, String> b) throws InterruptedException {
        final String uri = advantshopUri + "?new_order";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> entity = new HttpEntity<>(b, headers);
        RestTemplate restTemplate = new RestTemplate();
        Thread.sleep(1500);
        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.POST,
                entity,
                String.class,
                b);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Ошибка получения списка заказов");
            return null;
        } else {
            Gson gson = new Gson();
            ResponseDTO responseDTO = gson.fromJson(response.getBody() , ResponseDTO.class);
            if (!Objects.requireNonNull(responseDTO).getResult().equals("success")) {
                log.error("Ошибка получения списка заказов error- {} , entity - {}", Objects.requireNonNull(responseDTO).getError(), entity);
            }
            log.info("Заказ orderNumber = {} Передан в FrontPad c order_number = {}", frontPadOrderId, responseDTO.getOrder_number());
            return responseDTO;
        }
    }

    private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
