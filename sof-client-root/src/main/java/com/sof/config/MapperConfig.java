package com.sof.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper") // Se agrega el ("defaultMapper")
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }
}
