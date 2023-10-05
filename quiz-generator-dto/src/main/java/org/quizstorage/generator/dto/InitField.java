package org.quizstorage.generator.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record InitField<T>(
        @NotBlank
        String name,
        String description,
        @NotNull
        FieldType type,
        boolean required,
        @Nullable
        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
        T conf
) {}
