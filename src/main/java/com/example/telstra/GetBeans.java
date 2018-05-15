package com.example.telstra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.models.PropertiesCBR;

@Configuration
public class GetBeans {

	@Bean
    public PropertiesCBR getPropertiesCBR() {
		return new PropertiesCBR();
	}
}
