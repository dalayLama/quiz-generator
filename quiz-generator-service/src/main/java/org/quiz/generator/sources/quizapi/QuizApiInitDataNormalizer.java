package org.quiz.generator.sources.quizapi;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.configurations.properties.quizapi.QuizApiProperties;
import org.quiz.generator.sources.InitDataNormalizer;
import org.quizstorage.generator.dto.InitField;
import org.quiz.generator.sources.utils.NormalizeUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Component
@RequiredArgsConstructor
public class QuizApiInitDataNormalizer implements InitDataNormalizer<QuizApiInitData, Map<String, String>> {

    private final QuizApiProperties properties;

    @Override
    public Map<String, String> normalize(Collection<InitField> initFields, QuizApiInitData initData) {
        Map<String, String> map = NormalizeUtil.toMap(initFields, initData);
        map.put(properties.getApiKeyParamName(), properties.getToken());
        return map;
    }


}
