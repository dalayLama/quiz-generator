package org.quizstorage.generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record QuestionSet(
        @NotBlank
        String sourceId,
        @NotEmpty
        List<Question> questions
) {}
