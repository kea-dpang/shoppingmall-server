package com.example.shoppingmallserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShoppingmallServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingmallServerApplication.class, args);
	}

}
