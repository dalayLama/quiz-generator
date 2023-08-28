package org.quiz.generator.sources.fields;

import org.quizstorage.generator.dto.Difficulty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DifficultiesProvider implements SelectOptionsProvider {

    @Override
    public List<SelectOption> get() {
        return Arrays.stream(Difficulty.values())
                .map(this::toSelectOption)
                .toList();
    }

    private SelectOption toSelectOption(Difficulty difficulty) {
        return new SelectOption(difficulty.name(), difficulty.getDescription());
    }

}
