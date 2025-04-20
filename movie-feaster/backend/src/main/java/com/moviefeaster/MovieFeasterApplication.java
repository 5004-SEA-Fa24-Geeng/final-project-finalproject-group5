package com.moviefeaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieFeasterApplication {
    
    /**
     * Private constructor to prevent instantiation.
     */
     protected MovieFeasterApplication() {
        // Prevent instantiation
    }

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(MovieFeasterApplication.class, args);
    }
}
