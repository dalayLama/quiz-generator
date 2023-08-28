package org.quiz.generator.configurations;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.sources.fields.DefaultInitFieldsProvider;
import org.quiz.generator.sources.fields.DifficultiesProvider;
import org.quiz.generator.sources.fields.NumberInitFieldProvider;
import org.quiz.generator.sources.fields.SelectInitFieldProvider;
import org.quiz.generator.sources.quizapi.providers.select.QuizApiQuestionCategoriesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Configuration
@RequiredArgsConstructor
public class QuizApiConfiguration {

    private final QuizApiQuestionCategoriesProvider categoriesProvider;

    private final DifficultiesProvider difficultiesProvider;

    private NumberInitFieldProvider numberOfQuestionsInitFieldProvider() {
        return new NumberInitFieldProvider(
                "limit",
                "Number of questions",
                true,
                20,
                5
        );
    }

    public SelectInitFieldProvider categoriesInitFieldProvider() {
        return new SelectInitFieldProvider("category", "Category", false, categoriesProvider);
    }

    public SelectInitFieldProvider difficultyInitFieldProvider() {
        return new SelectInitFieldProvider(
                "difficulty",
                "Difficulty",
                false,
                difficultiesProvider
        );
    }

    @Bean
    public DefaultInitFieldsProvider quizApiInitFieldsProvider() {
        return new DefaultInitFieldsProvider(List.of(
                numberOfQuestionsInitFieldProvider(),
                categoriesInitFieldProvider(),
                difficultyInitFieldProvider()
        ));
    }

}
