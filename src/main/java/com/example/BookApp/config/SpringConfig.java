package com.example.BookApp.config;

import com.example.BookApp.model.BookInit;
import com.example.BookApp.dto.BookInitDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapperToBookInitDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<BookInit, BookInitDTO> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().convertByteToBooleanIsBestseller(source.getIsBestseller());
                map().setRating((byte) 4);
                map().setStatus("ARCHIVED");
            }
        };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

}
