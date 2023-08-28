package org.quiz.generator.sources;

import org.quizstorage.generator.dto.InitField;

import java.util.Collection;

public interface InitDataNormalizer<T extends InitData, V> {

    V normalize(Collection<InitField> initFields, T initData);

}
