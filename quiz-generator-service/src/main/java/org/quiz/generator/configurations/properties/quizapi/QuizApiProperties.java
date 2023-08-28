package org.quiz.generator.configurations.properties.quizapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "quiz-sources.quiz-api")
@Setter
@Getter
public class QuizApiProperties {

    private String url;

    private String apiKeyParamName;

    private String token;

    private QuizApiPathsProperties paths;

    private Map<String, String> categories;
}
