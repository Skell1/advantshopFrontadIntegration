package org.example.advantshopfrontadintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class NotPayedOrdersService {
    private static final String fileName = "notPayedOrders";

    private final TelegramBot telegramBot;

    public NotPayedOrdersService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void init() {
        Set<Integer> notPayedOrders = new HashSet<>();
        writeNotPayedOrders(notPayedOrders);
    }

    public void writeNotPayedOrders(Set<Integer> notPayedOrders) {
        try {
            File fileOne=new File(fileName);
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(notPayedOrders);
            oos.flush();
            oos.close();
            fos.close();
        } catch(Exception e) {
            log.error("Ошибка записи в файл неоплаченных заказов");
            telegramBot.logErrorMessage("Ошибка записи в файл неоплаченных заказов");
        }

    }


    public Set<Integer> readNotPayedOrders() {
        try {
            File toRead=new File(fileName);
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);

            Set<Integer> setInFile=(Set<Integer>)ois.readObject();

            ois.close();
            fis.close();

            return setInFile;

        } catch(Exception e) {
            log.error("Ошибка чтения из файла неоплаченных заказов");
            telegramBot.logErrorMessage("Ошибка чтения из файла неоплаченных заказов");
        }

        return null;
    }
}
