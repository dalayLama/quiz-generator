package org.quiz.generator.sources.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quiz.generator.mappers.TriviaQuestionMapper;
import org.quizstorage.generator.dto.Difficulty;
import org.quizstorage.generator.dto.Question;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestion;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TriviaQuestionMapperTest {

    private static final TriviaQuestion TRIVIA_QUESTION = TriviaQuestion.builder()
            .question("question")
            .category("category")
            .type(TriviaQuestionType.MULTIPLE)
            .correctAnswer("correctAnswer")
            .incorrectAnswers(Set.of("incorrect answer 1", "incorect answer 2"))
            .difficulty(Difficulty.EASY)
            .build();

    private final TriviaQuestionMapper mapper = Mappers.getMapper(TriviaQuestionMapper.class);

    @Test
    void shouldConvertTriviaQuestionToQuestion() {
        Question expectedResult = Question.builder()
                .question(TRIVIA_QUESTION.question())
                .category(TRIVIA_QUESTION.category())
                .answers(Set.of("correctAnswer", "incorrect answer 1", "incorect answer 2"))
                .correctAnswers(Set.of("correctAnswer"))
                .multiplyAnswers(false)
                .difficulty(TRIVIA_QUESTION.difficulty())
                .build();

        Question result = mapper.toQuestion(TRIVIA_QUESTION);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);

    }

}