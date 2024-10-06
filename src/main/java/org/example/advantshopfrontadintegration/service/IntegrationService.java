package org.example.advantshopfrontadintegration.service;

import lombok.val;
import org.example.advantshopfrontadintegration.converter.FrontPadOrderConverter;
import org.example.advantshopfrontadintegration.dto.AdvantShop.DataItemDTO;
import org.example.advantshopfrontadintegration.dto.AdvantShop.OrdersDTO;
import org.example.advantshopfrontadintegration.util.LastOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class IntegrationService {
    private static final Logger log = LoggerFactory.getLogger(IntegrationService.class);

    private final AdvantShopService advantShopService;
    private final FrontPadOrderConverter frontPadOrderConverter;
    private final FrontpadService frontpadService;
    private final LastOrderService lastOrderService;
    private final TelegramBot telegramBot;
    private final NotPayedOrdersService notPayedOrdersService;

    public IntegrationService(AdvantShopService advantShopService, FrontPadOrderConverter frontPadOrderConverter, FrontpadService frontpadService, LastOrderService lastOrderService, TelegramBot telegramBot, NotPayedOrdersService notPayedOrdersService) {
        this.advantShopService = advantShopService;
        this.frontPadOrderConverter = frontPadOrderConverter;
        this.frontpadService = frontpadService;
        this.lastOrderService = lastOrderService;
        this.telegramBot = telegramBot;
        this.notPayedOrdersService = notPayedOrdersService;
    }


    @Scheduled(initialDelay = 5, fixedDelay = 900000)
    public void startIntegration() {
        try {
            log.info("Starting integration...");
            //telegramBot.logInfoMessage("Starting integration...");
            OrdersDTO ordersDTO = advantShopService.getOrderList(0);
            if (Objects.isNull(ordersDTO))
                return;
            Integer newLastOrder = transferNewOrders(ordersDTO);

            while (ordersDTO.getTotalPageCount() > ordersDTO.getPageIndex()) {
                ordersDTO = advantShopService.getOrderList(ordersDTO.getPageIndex() + 1);
                Integer newLastOrderTemp = transferNewOrders(ordersDTO);
                if (newLastOrderTemp > newLastOrder) newLastOrder = newLastOrderTemp;
            }

            lastOrderService.writeNewLastOrder(newLastOrder);

            searchNewPayedOrders();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            telegramBot.logErrorMessage("error Integration " + e);
        }

    }

    public Integer transferNewOrders(OrdersDTO ordersDTO) throws InterruptedException {
        if (Objects.isNull(ordersDTO.getDataItems()) || ordersDTO.getDataItems().isEmpty())
            log.info("Нет новых заказов");
        Integer lastOrder = lastOrderService.getLastOrder();
        Set<Integer> newNotPayedOrders = new HashSet<>();
        if (Objects.isNull(lastOrder)) return null;
        int newLastOrder = lastOrder;
        for (DataItemDTO dataItemDTO : ordersDTO.getDataItems()) {
            if (dataItemDTO.getId() <= lastOrder) continue;
            if (dataItemDTO.getId() > newLastOrder) newLastOrder = dataItemDTO.getId();
            val frontPadOrder = frontPadOrderConverter.convertToMultiValueMap(dataItemDTO);
            frontpadService.postNewOrder(dataItemDTO.getId(), frontPadOrder);
            if (!dataItemDTO.isPaid()) {
                newNotPayedOrders.add(dataItemDTO.getId());
            }
        }
        addNewNotPayedOrders(newNotPayedOrders);
        return newLastOrder;
    }

    public void searchNewPayedOrders() {
        try {
        Set<Integer> notPayedOrders = notPayedOrdersService.readNotPayedOrders();
        if (Objects.nonNull(notPayedOrders)) {
            log.error("Ошибка чтения из файла неоплаченных заказов");
            telegramBot.logErrorMessage("Ошибка чтения из файла неоплаченных заказов");
        }
        for(Integer orderId : notPayedOrders) {
            DataItemDTO dataItemDTO = advantShopService.getOrderById(orderId);
            if (Objects.isNull(dataItemDTO)) {

                continue;
            }
            if (dataItemDTO.isPaid()) {
                val frontPadOrder = frontPadOrderConverter.convertToMultiValueMap(dataItemDTO);
                frontpadService.postNewOrder(dataItemDTO.getId(), frontPadOrder);
                notPayedOrders.remove(orderId);
                continue;
            }
            if (LocalDateTime.parse(dataItemDTO.getDate()).toLocalDate().plusWeeks(1).isAfter(LocalDate.now())) {
                notPayedOrders.remove(orderId);
            }
        }

        notPayedOrdersService.writeNotPayedOrders(notPayedOrders);

        } catch (Exception e) {
            log.error("Ошибка при повторной проверке неоплаченных заказов {}", e.getMessage());
            telegramBot.logErrorMessage("Ошибка при повторной проверке неоплаченных заказов " + e.getMessage());
        }
    }

    public void addNewNotPayedOrders(Set<Integer> newNotPayedOrders) {
        if (Objects.isNull(newNotPayedOrders) || newNotPayedOrders.isEmpty()) return;
        try {
            Set<Integer> notPayedOrders = notPayedOrdersService.readNotPayedOrders();
            if (Objects.nonNull(notPayedOrders)) {
                log.error("Ошибка чтения из файла неоплаченных заказов");
                telegramBot.logErrorMessage("Ошибка чтения из файла неоплаченных заказов");
            }
            notPayedOrders.addAll(newNotPayedOrders);

            notPayedOrdersService.writeNotPayedOrders(notPayedOrders);
        } catch (Exception e) {
            log.error("Ошибка добавления новых неоплачнных заказов неоплаченных заказов {}", e.getMessage());
            telegramBot.logErrorMessage("Ошибка добавления новых неоплачнных заказов неоплаченных заказов " + e.getMessage());
        }
    }


}
