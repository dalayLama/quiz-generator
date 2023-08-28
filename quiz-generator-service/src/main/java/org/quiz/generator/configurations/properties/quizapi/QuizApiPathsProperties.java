package org.quiz.generator.configurations.properties.quizapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "quiz-sources.quiz-api.path")
@Getter
@Setter
public class QuizApiPathsProperties {

    private String questions;

}
