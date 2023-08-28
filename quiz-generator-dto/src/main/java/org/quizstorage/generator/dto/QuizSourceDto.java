package org.quizstorage.generator.dto;

import jakarta.validation.constraints.NotBlank;

public record QuizSourceDto(
        @NotBlank
        String id,
        @NotBlank
        String name,
        String description
) {}
