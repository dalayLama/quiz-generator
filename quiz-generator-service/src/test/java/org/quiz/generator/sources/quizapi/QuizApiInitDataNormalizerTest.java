package org.quiz.generator.sources.quizapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quiz.generator.configurations.properties.quizapi.QuizApiProperties;
import org.quiz.generator.exceptions.MissedRequiredInitFieldException;
import org.quizstorage.generator.dto.FieldType;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.NumberFormat;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuizApiInitDataNormalizerTest {

    @Mock
    private QuizApiProperties properties;

    @InjectMocks
    private QuizApiInitDataNormalizer initDataNormalizer;

    private static final String API_KEY_PARAM_NAME = "apiKey";

    private static final String TOKEN = "token";

    private static final List<InitField<?>> INIT_FIELDS = List.of(
            FieldType.NUMBER.createInitField(
                    "f1", null, false, new NumberFormat(0, 5)),
            FieldType.NUMBER.createInitField(
                    "f2", null, true, new NumberFormat(0, 5))
    );

    @Test
    void shouldNormalizeInitData() {
        initProperties();
        QuizApiInitData quizApiInitData = new QuizApiInitData(Map.of(
                "f1", "5",
                "f2", "6"
        ));
        Map<String, String> expectedResult = Map.of(
                "f1", "5",
                "f2", "6",
                API_KEY_PARAM_NAME, TOKEN
        );
        Map<String, String> result = initDataNormalizer.normalize(INIT_FIELDS, quizApiInitData);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowMissedParamException() {
        QuizApiInitData quizApiInitData = new QuizApiInitData(Map.of(
                "f1", "5"
        ));
        assertThatThrownBy(() -> initDataNormalizer.normalize(INIT_FIELDS, quizApiInitData))
                .isInstanceOf(MissedRequiredInitFieldException.class)
                .hasMessage("Required field %s is absent".formatted("f2"));
    }

    @Test
    void shouldNotThrowExceptionWhenNotRequiredParamsIsAbsent() {
        initProperties();
        QuizApiInitData quizApiInitData = new QuizApiInitData(Map.of(
                "f2", "6"
        ));
        Map<String, String> expectedResult = Map.of(
                "f2", "6",
                API_KEY_PARAM_NAME, TOKEN
        );
        Map<String, String> result = initDataNormalizer.normalize(INIT_FIELDS, quizApiInitData);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    private void initProperties() {
        given(properties.getApiKeyParamName()).willReturn(API_KEY_PARAM_NAME);
        given(properties.getToken()).willReturn(TOKEN);
    }

}