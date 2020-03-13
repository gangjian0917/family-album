package com.vbeesoft.familyalbum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableCaching
public class FamilyAlbumApplication extends SpringBootServletInitializer {
    @RequestMapping("/")
    @Cacheable(cacheNames = "name", key = "#name")
    public String home(String name) {
        return "Hello World!-------" + name;
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(FamilyAlbumApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FamilyAlbumApplication.class, args);
    }

}
