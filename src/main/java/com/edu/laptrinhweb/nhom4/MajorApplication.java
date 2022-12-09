package com.edu.laptrinhweb.nhom4;

import com.edu.laptrinhweb.nhom4.configuration.MvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.ResourceHandlerRegistrationCustomizer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@SpringBootApplication
public class MajorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajorApplication.class, args);
    }

}
