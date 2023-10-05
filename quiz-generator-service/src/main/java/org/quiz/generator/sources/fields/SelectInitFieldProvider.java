package org.quiz.generator.sources.fields;

import lombok.AllArgsConstructor;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.FieldType;
import org.quizstorage.generator.dto.SelectFormat;
import org.quizstorage.generator.dto.SelectOption;

import java.util.List;

@AllArgsConstructor
public class SelectInitFieldProvider implements InitFieldProvider {

    private final String name;

    private final String description;

    private final boolean required;

    private final boolean multiValue;

    private final SelectOptionsProvider selectOptionsProvider;

    @Override
    public InitField<SelectFormat> get() {
        List<SelectOption> selectValues = selectOptionsProvider.get();
        SelectFormat selectFormat = new SelectFormat(multiValue, selectValues);
        return FieldType.SELECT.createInitField(name, description, required, selectFormat);
    }

}
