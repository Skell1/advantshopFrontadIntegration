package org.example.advantshopfrontadintegration.service;

import org.example.advantshopfrontadintegration.converter.FrontPadOrderConverter;
import org.example.advantshopfrontadintegration.dto.AdvantShop.DataItemDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.OrdersDTO;
import org.example.advantshopfrontadintegration.dto.Frontpad.FrontPadOrder;
import org.example.advantshopfrontadintegration.util.LastOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IntegrationService {
    private static final Logger log = LoggerFactory.getLogger(IntegrationService.class);

    private final AdvantShopService advantShopService;
    private final FrontPadOrderConverter frontPadOrderConverter;
    private final FrontpadService frontpadService;


    public IntegrationService(AdvantShopService advantShopService, FrontPadOrderConverter frontPadOrderConverter, FrontpadService frontpadService) {
        this.advantShopService = advantShopService;
        this.frontPadOrderConverter = frontPadOrderConverter;
        this.frontpadService = frontpadService;
    }


    @Scheduled(initialDelay = 5, fixedDelay = 900000)
    public void startIntegration() {
        log.info("Starting integration...");
        OrdersDTO ordersDTO = advantShopService.getOrderList();
        if (Objects.isNull(ordersDTO)) return;
        Integer lastOrder = LastOrder.getLastOrder();
        if (Objects.isNull(lastOrder)) return;

        if (ordersDTO.getTotalItemsCount()==lastOrder) {
            log.info("Нет новых заказов");
            return;
        } else if (ordersDTO.getTotalItemsCount()<lastOrder) {
            log.error("TotalItemsCount - {}, lastOrder - {}. Неверные значения", ordersDTO.getTotalItemsCount(), lastOrder);
            return;
        }

        while (lastOrder < ordersDTO.getTotalItemsCount()) {
            lastOrder++;
            DataItemDTO dataItemDTO = advantShopService.getOrderById(lastOrder);

            FrontPadOrder frontPadOrder = frontPadOrderConverter.convert(dataItemDTO);

            frontpadService.postNewOrder(frontPadOrder);

            LastOrder.writeNewLastOrder(lastOrder);
            lastOrder = LastOrder.getLastOrder();
        }

    }


}
