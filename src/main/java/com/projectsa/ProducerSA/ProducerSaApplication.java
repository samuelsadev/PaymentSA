package com.projectsa.ProducerSA;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ProducerSaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerSaApplication.class, args);
	}

}
