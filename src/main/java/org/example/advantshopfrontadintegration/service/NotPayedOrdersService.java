package org.example.advantshopfrontadintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotPayedOrdersService {
    private static final Path path = Paths.get("notPayedOrders.txt");

    private final TelegramBot telegramBot;

    public NotPayedOrdersService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public Set<Integer> readNotPayedOrders() {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            if (Objects.isNull(lines) || lines.isEmpty()) {
                return new HashSet<>();
            }
            String line = lines.get(0);
            log.info("Прочитанные неоплаченные заказы : {}", line);
            line = line.replace("[","").replace("]","").replace(" ","");
            if (line.isEmpty()) {
                log.info("Cписок прочитанных неоплаченные заказов пуст");
                return new HashSet<>();
            }
            return Arrays.stream(line.split(",")).map(Integer::parseInt)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error("Ошибка чтения из файла неоплаченных заказов {}", e);
            telegramBot.logErrorMessage("Ошибка чтения из файла неоплаченных заказов " + e.getMessage());
        }
        return new HashSet<>();
    }

    public void writeNotPayedOrders(Set<Integer> notPayedOrders) {
        try {
            Files.write(path, List.of(notPayedOrders.toString()), StandardCharsets.UTF_8);
            log.info("Записанные неоплаченные заказы : {}", notPayedOrders);
        } catch (IOException ex) {
            log.error("Ошибка записи в файл неоплаченных заказов {}", ex);
            telegramBot.logErrorMessage("Ошибка записи в файл неоплаченных заказов "+ ex.getMessage());
        }

    }

}
