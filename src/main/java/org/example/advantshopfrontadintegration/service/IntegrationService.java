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
        try {
            log.info("Starting integration...");
            OrdersDTO ordersDTO = advantShopService.getOrderList(0);
            if (Objects.isNull(ordersDTO))
                return;
            Integer newLastOrder = transferNewOrders(ordersDTO);

            while (ordersDTO.getTotalPageCount() > ordersDTO.getPageIndex()) {
                ordersDTO = advantShopService.getOrderList(ordersDTO.getPageIndex() + 1);
                Integer newLastOrderTemp = transferNewOrders(ordersDTO);
                if (newLastOrderTemp > newLastOrder) newLastOrder = newLastOrderTemp;
            }
            LastOrder.writeNewLastOrder(newLastOrder);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public Integer transferNewOrders(OrdersDTO ordersDTO) {
        if (Objects.isNull(ordersDTO.getDataItems()) || ordersDTO.getDataItems().isEmpty())
            log.info("Нет новых заказов");
        Integer lastOrder = LastOrder.getLastOrder();
        if (Objects.isNull(lastOrder)) return null;
        int newLastOrder = lastOrder;
        for (DataItemDTO dataItemDTO : ordersDTO.getDataItems()) {
            if (dataItemDTO.getId() <= lastOrder) continue;
            if (dataItemDTO.getId() > newLastOrder) newLastOrder = dataItemDTO.getId();
            FrontPadOrder frontPadOrder = frontPadOrderConverter.convert(dataItemDTO);
            frontpadService.postNewOrder(frontPadOrder, dataItemDTO.getId());

        }
        return newLastOrder;
    }


}
