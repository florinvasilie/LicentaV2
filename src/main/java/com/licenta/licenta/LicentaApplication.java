package com.licenta.licenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = { "com.licenta.licenta"} )
@SpringBootApplication
//@EnableWebMvc
public class LicentaApplication {
	public static void main(String[] args) {
		SpringApplication.run(LicentaApplication.class, args);
	}
}
