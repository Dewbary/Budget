package org.example;

import org.example.resource.Program;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PlaidApplication {

    public static void main(final String[] args) {
        ApplicationContext context = SpringApplication.run(PlaidApplication.class, args);
    }
}
