package org.quiz.generator.sources.fields;

import org.quizstorage.generator.dto.SelectOption;

import java.util.List;

public interface SelectOptionsProvider {

    List<SelectOption> get();

}
