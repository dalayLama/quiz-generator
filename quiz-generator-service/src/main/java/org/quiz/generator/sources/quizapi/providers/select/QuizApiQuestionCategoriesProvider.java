package org.quiz.generator.sources.quizapi.providers.select;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.configurations.properties.quizapi.QuizApiProperties;
import org.quiz.generator.sources.fields.SelectOption;
import org.quiz.generator.sources.fields.SelectOptionsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Component
@RequiredArgsConstructor
public class QuizApiQuestionCategoriesProvider implements SelectOptionsProvider {

    private final QuizApiProperties properties;

    @Override
    public List<SelectOption> get() {
        return properties.getCategories().entrySet().stream()
                .map(entry -> new SelectOption(entry.getKey(), entry.getValue()))
                .toList();
    }

}
