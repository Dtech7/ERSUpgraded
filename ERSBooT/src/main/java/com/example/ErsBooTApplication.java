package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.repository.EmployeeRepository;

@SpringBootApplication
public class ErsBooTApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErsBooTApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return(rgs) ->{
			EmployeeRepository err;
		};
	}
}
