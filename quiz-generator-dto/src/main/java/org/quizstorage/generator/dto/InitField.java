package org.quizstorage.generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record InitField(
        @NotBlank
        String name,
        String description,
        @NotNull
        FieldType type,
        boolean required,
        @Nullable
        Object conf
) {}
