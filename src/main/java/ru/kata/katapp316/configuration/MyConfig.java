package ru.kata.katapp316.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("ru.kata.katapp316")
public class MyConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
