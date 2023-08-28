package org.quizstorage.generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record Question(
        @NotBlank
        String question,
        @NotEmpty
        Set<String> answers,
        @NotEmpty
        Set<String> correctAnswers,
        String category,
        boolean multiplyAnswers,
        @NotNull
        Difficulty difficulty
) {
}
