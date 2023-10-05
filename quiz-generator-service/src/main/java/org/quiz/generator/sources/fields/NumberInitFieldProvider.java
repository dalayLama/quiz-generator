package org.quiz.generator.sources.fields;

import lombok.RequiredArgsConstructor;
import org.quizstorage.generator.dto.FieldType;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.NumberFormat;

@RequiredArgsConstructor
public class NumberInitFieldProvider implements InitFieldProvider {

    private final String name;

    private final String description;

    private final boolean required;

    private final int maxValue;

    private final int minValue;

    public NumberInitFieldProvider(String name, String description, boolean required) {
        this(name, description, required, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    @Override
    public InitField<NumberFormat> get() {
        NumberFormat numberFormat = new NumberFormat(maxValue, minValue);
        return FieldType.NUMBER.createInitField(name, description, required, numberFormat);
    }

}
