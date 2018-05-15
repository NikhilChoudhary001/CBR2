package com.example.telstra;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.example.models.PropertiesCBR;


@SpringBootApplication
@ComponentScan(basePackages = "com.example")

public class TelstraApplication {	
	 	
	private static final Logger logger = Logger.getLogger(TelstraApplication.class);	
	

	public static Router router() {
		return new Router();
	}		
	
	@Bean
	public PropertiesCBR getPropertiesCBR() {
		return new PropertiesCBR();
	}
	
	public static void main(String[] args) {		
		
		SpringApplication.run(TelstraApplication.class, args);
		
		router().init();
		router().exec();

	}
		
	 
}