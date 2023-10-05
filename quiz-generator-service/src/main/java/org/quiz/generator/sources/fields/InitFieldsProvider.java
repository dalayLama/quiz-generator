package org.quiz.generator.sources.fields;

import org.quizstorage.generator.dto.InitField;

import java.util.stream.Stream;

public interface InitFieldsProvider {

    Stream<InitField<?>> getInitFields();

}
