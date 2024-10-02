package org.example.advantshopfrontadintegration.util;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.service.TelegramBot;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
@Component
@Slf4j
public class LastOrderService {
    private static Path path = Paths.get("lastOrderId");

    private final TelegramBot telegramBot;

    public LastOrderService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public Integer getLastOrder() {
        Scanner scanner;
        try {
            scanner = new Scanner(path);
            if (scanner.hasNextInt()) {
                Integer lastOrder = scanner.nextInt();
                scanner.close();
                return lastOrder;
            } else {
                scanner.close();
                log.error("Ошибочная запись о последнем заказе");
                telegramBot.logErrorMessage("Ошибочная запись о последнем заказе");
            }
            scanner.close();
        } catch (IOException e) {
            log.error("Ошибка чтения lastOrder. error - {}", e.getMessage());
            telegramBot.logErrorMessage("Ошибка чтения lastOrder. error - " + e.getMessage());
        }
        return null;
    }

    public void writeNewLastOrder(Integer lastOrder) {
        try {
            Files.write(path, List.of(lastOrder.toString()), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("Ошибка записи нового lastOrder. error - {}", ex.getMessage());
            telegramBot.logErrorMessage("Ошибка записи нового lastOrder. error - " + ex.getMessage());
        }

    }
}
