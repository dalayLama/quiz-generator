package org.quiz.generator.sources.opentriviadb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quiz.generator.exceptions.MissedRequiredInitFieldException;
import org.quizstorage.generator.dto.InitField;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TriviaInitDataNormalizerTest {

    private static final List<InitField> INIT_FIELDS = List.of(
            InitField.number("f1", null, false, 0, 5, true),
            InitField.number("f2", null, true, 0, 5, true)
    );

    @InjectMocks
    private TriviaInitDataNormalizer normalizer;

    @Test
    void shouldNormalizeInitData() {
        TriviaInitData initData = new TriviaInitData(Map.of(
                "f1", "5",
                "f2", "6"
        ));
        Map<String, String> expectedResult = Map.of(
                "f1", "5",
                "f2", "6"
        );
        Map<String, String> result = normalizer.normalize(INIT_FIELDS, initData);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowMissedParamException() {
        TriviaInitData quizApiInitData = new TriviaInitData(Map.of(
                "f1", "5"
        ));
        assertThatThrownBy(() -> normalizer.normalize(INIT_FIELDS, quizApiInitData))
                .isInstanceOf(MissedRequiredInitFieldException.class)
                .hasMessage("Required field %s is absent".formatted("f2"));
    }

    @Test
    void shouldNotThrowExceptionWhenNotRequiredParamsIsAbsent() {
        TriviaInitData quizApiInitData = new TriviaInitData(Map.of(
                "f2", "6"
        ));
        Map<String, String> expectedResult = Map.of(
                "f2", "6"
        );
        Map<String, String> result = normalizer.normalize(INIT_FIELDS, quizApiInitData);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}