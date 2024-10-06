package org.example.advantshopfrontadintegration;

import lombok.extern.slf4j.Slf4j;
import org.example.advantshopfrontadintegration.service.NotPayedOrdersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class AdvantshopFrontadIntegrationApplication {

	public static void main(String[] args) {
		if (args.length != 0 && args[0].equals("init")) {
			log.info("init file NotPayedOrders");
			NotPayedOrdersService.init();
		}
		SpringApplication.run(AdvantshopFrontadIntegrationApplication.class, args);
	}

}
