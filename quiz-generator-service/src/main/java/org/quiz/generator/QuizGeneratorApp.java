package org.quiz.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuizGeneratorApp {

    public static void main(String[] args) {
        SpringApplication.run(QuizGeneratorApp.class, args);
    }

}
