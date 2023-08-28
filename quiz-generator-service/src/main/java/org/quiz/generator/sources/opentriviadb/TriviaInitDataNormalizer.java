package org.quiz.generator.sources.opentriviadb;

import org.quiz.generator.sources.InitDataNormalizer;
import org.quizstorage.generator.dto.InitField;
import org.quiz.generator.sources.utils.NormalizeUtil;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class TriviaInitDataNormalizer implements InitDataNormalizer<TriviaInitData, Map<String, String>> {

    @Override
    public Map<String, String> normalize(Collection<InitField> initFields, TriviaInitData initData) {
        return NormalizeUtil.toMap(initFields, initData);
    }

}
