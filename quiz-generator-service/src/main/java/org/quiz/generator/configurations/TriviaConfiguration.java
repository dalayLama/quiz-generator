package org.quiz.generator.configurations;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.sources.fields.DefaultInitFieldsProvider;
import org.quiz.generator.sources.fields.DifficultiesProvider;
import org.quiz.generator.sources.fields.NumberInitFieldProvider;
import org.quiz.generator.sources.fields.SelectInitFieldProvider;
import org.quiz.generator.sources.opentriviadb.providers.select.QuestionCategoriesProvider;
import org.quiz.generator.sources.opentriviadb.providers.select.QuestionTypesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TriviaConfiguration {

    private final QuestionCategoriesProvider categoriesProvider;

    private final QuestionTypesProvider typesProvider;

    private final DifficultiesProvider difficultiesProvider;

    private NumberInitFieldProvider numberOfQuestionsInitFieldProvider() {
        return new NumberInitFieldProvider(
                "amount",
                "Number of questions",
                true,
                50,
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

    public SelectInitFieldProvider typeInitFieldProvider() {
        return new SelectInitFieldProvider("type", "Type", false, typesProvider);
    }

    @Bean
    public DefaultInitFieldsProvider triviaInitFieldsProvider() {
        return new DefaultInitFieldsProvider(List.of(
                numberOfQuestionsInitFieldProvider(),
                difficultyInitFieldProvider(),
                categoriesInitFieldProvider(),
                typeInitFieldProvider()
        ));
    }


}
