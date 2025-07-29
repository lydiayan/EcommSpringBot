package com.example.mallordercmp_ssoclient;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MallOrderCmpSsoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderCmpSsoClientApplication.class, args);
    }
}
