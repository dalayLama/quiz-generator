package org.quiz.generator.sources.fields;

import lombok.AllArgsConstructor;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.FieldType;

import java.util.List;

@AllArgsConstructor
public class SelectInitFieldProvider implements InitFieldProvider {

    private final String name;

    private final String description;

    private final boolean required;

    private final SelectOptionsProvider selectOptionsProvider;

    @Override
    public InitField get() {
        List<SelectOption> selectValues = selectOptionsProvider.get();
        return FieldType.SELECT.createInitField(name, description, required, selectValues);
    }

}
