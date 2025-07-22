package com.example.mallordercmpserver;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.mallordercmpserver.service.OpenOrderService;

@SpringBootApplication
public class MallOrderCmpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderCmpServerApplication.class, args);
    }


    @Bean
    public ToolCallbackProvider orderTools(OpenOrderService openOrderService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(openOrderService)
                .build();
    }
/*
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }*/

}
