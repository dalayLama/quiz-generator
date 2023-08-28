package org.quiz.generator.sources.quizapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quiz.generator.mappers.QuizApiQuestionMapper;
import org.quiz.generator.sources.InitDataNormalizer;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.Question;
import org.quizstorage.generator.dto.QuestionSet;
import org.quiz.generator.sources.fields.InitFieldsProvider;
import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuizApiSourceTest {

    @Mock
    private InitFieldsProvider quizApiInitFieldsProvider;

    @Mock
    private InitDataNormalizer<QuizApiInitData, Map<String, String>> initDataNormalizer;

    @Mock
    private QuizApiClient quizApiClient;

    @Mock
    private QuizApiQuestionMapper questionMapper;

    @InjectMocks
    private QuizApiSource source;

    @Test
    void shouldGenerateQuestions() {
        InitField initField = InitField.builder().build();
        Stream<InitField> initFieldStream = Stream.of(initField);
        QuizApiInitData quizApiInitData = new QuizApiInitData();
        Map<String, String> params = Map.of();
        QuizApiQuestion apiQuestion = QuizApiQuestion.builder().build();
        Question question = Question.builder().build();
        QuestionSet expectedResult = new QuestionSet(source.id(), List.of(question));

        given(quizApiInitFieldsProvider.getInitFields()).willReturn(initFieldStream);
        given(initDataNormalizer.normalize(List.of(initField), quizApiInitData)).willReturn(params);
        given(quizApiClient.questions(params)).willReturn(List.of(apiQuestion));
        given(questionMapper.toQuestion(apiQuestion)).willReturn(question);

        QuestionSet result = source.generate(quizApiInitData);
        assertThat(result).isEqualTo(expectedResult);
    }

}