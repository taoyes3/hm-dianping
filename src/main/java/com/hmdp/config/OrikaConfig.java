package com.hmdp.config;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taoyes3
 * @date 2022/10/2 20:22
 */
@Configuration
@Slf4j
public class OrikaConfig {
    @Bean
    public MapperFactory mapperFactory() {
        log.info("OrikaConfig MapperFactory...");
        return new DefaultMapperFactory.Builder().build();
    }
    
    @Bean
    public MapperFacade mapperFacade() {
        log.info("OrikaConfig MapperFacade...");
        return mapperFactory().getMapperFacade();
    }
}
