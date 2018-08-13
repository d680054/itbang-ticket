package com.hj.eventbrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.hj.eventbrite","com.hj.selenium"})
public class ItBangTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItBangTicketApplication.class, args);
	}
}
