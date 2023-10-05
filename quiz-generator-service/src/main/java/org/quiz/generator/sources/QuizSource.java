package org.quiz.generator.sources;

import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.QuestionSet;

import java.util.List;

public interface QuizSource<T extends InitData> {

    String id();

    String name();

    String description();

    List<InitField<?>> initFields();

    QuestionSet generate(T initData);

    Class<T> initDataClass();

}
