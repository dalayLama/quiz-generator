package org.quiz.generator.sources.opentriviadb.providers.select;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.sources.fields.SelectOption;
import org.quiz.generator.sources.fields.SelectOptionsProvider;
import org.quiz.generator.sources.opentriviadb.TriviaClient;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionCategory;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionCategoriesProvider implements SelectOptionsProvider {

    private final TriviaClient service;

    @Override
    public List<SelectOption> get() {
        TriviaQuestionCategoryResponse response = service.cagetories();
        return response.categories().stream()
                .map(this::toSelectOption)
                .toList();
    }

    private SelectOption toSelectOption(TriviaQuestionCategory category) {
        return new SelectOption(String.valueOf(category.id()), category.name());
    }

}
