package com.movie.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieDataServiceApplication.class, args);
	}

}
