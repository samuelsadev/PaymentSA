package com.projectsa.PaymentSA;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PaymentSaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSaApplication.class, args);
	}

}
