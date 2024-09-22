//package org.example.advantshopfrontadintegration.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Scanner;
//
//@Component
//@Slf4j
//public class DateFrom {
//
//    private static Path path = Paths.get("dateFrom");
//
//
//    public static LocalDateTime getDateFrom() {
//        Scanner scanner;
//        try {
//            scanner = new Scanner(path);
//            if (scanner.hasNext()) {
//                String lastOrder = scanner.next();
//                scanner.close();
//                return LocalDateTime.;
//            } else {
//                scanner.close();
//                log.error("Ошибочная запись о последнем заказе");
//            }
//            scanner.close();
//        } catch (IOException e) {
//            log.error("Ошибка чтения lastOrder. error - {}", e.getMessage());
//        }
//        return null;
//    }
//
//    public static void writeNewDate(LocalDateTime dateFrom) {
//        try {
//            Files.write(path, List.of(lastOrder.toString()), StandardCharsets.UTF_8);
//        } catch (IOException ex) {
//            log.error("Ошибка записи нового lastOrder. error - {}", ex.getMessage());
//        }
//
//    }
//}
