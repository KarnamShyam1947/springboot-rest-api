package com.shyam.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}
