package org.quiz.generator.sources.opentriviadb.providers.select;

import org.quizstorage.generator.dto.SelectOption;
import org.quiz.generator.sources.fields.SelectOptionsProvider;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component(value = "triviaQuestionTypesProvider")
public class QuestionTypesProvider implements SelectOptionsProvider {

    @Override
    public List<SelectOption> get() {
        return Arrays.stream(TriviaQuestionType.values())
                .map(this::toSelectOption)
                .toList();
    }

    private SelectOption toSelectOption(TriviaQuestionType questionType) {
        return new SelectOption(questionType.name().toLowerCase(), questionType.getDescription());
    }

}
