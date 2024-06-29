package com.sixplus.server.api;

import com.sixplus.server.api.domain.HotelEntity;
import com.sixplus.server.api.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApiApplication {
	private static final Logger log = LoggerFactory.getLogger(ApiApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(HotelRepository repository) {
		return (args) -> {
			// save a few customers
//			repository.save(
//					HotelEntity.of("The LINE LA", "3515 Wilshire Blvd, Los Angeles, CA 90010", "+12133817411")
//			);
//
//			// fetch all hotels
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			repository.findAll().forEach(customer -> {
//				log.info(customer.toString());
//			});
//			log.info("");

//			// fetch an individual customer by ID
//			HotelEntity hotel = repository.findByHotelId(1L);
//			log.info("Hotel found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(hotel.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
			log.info("");
		};
	}
}
