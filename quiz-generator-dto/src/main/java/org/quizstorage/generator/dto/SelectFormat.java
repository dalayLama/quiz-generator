package org.quizstorage.generator.dto;

import java.util.List;

public record SelectFormat(
        boolean multiValue,
        List<SelectOption> selectOptions
) {
}
