package org.quiz.generator.mappers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quizstorage.generator.dto.Difficulty;
import org.quizstorage.generator.dto.Question;
import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;
import org.quiz.generator.sources.quizapi.dto.QuizApiTag;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuizApiQuestionMapperTest {

    private static final QuizApiQuestion QUIZ_API_QUESTION = QuizApiQuestion.builder()
            .id(1069L)
            .question("What will happen If you run the command \"init 0\" in your terminal")
            .answers(Map.of(
                    "answer_a", "Shut down the system",
                    "answer_b", "Reboot the system"
            ))
            .multiplyCorrectAnswers(false)
            .correctAnswers(Map.of(
                    "answer_a_correct", true,
                    "answer_b_correct", false
            ))
            .tags(Set.of(new QuizApiTag("Linux")))
            .category("Linux")
            .difficulty(Difficulty.MEDIUM)
            .build();

    private final QuizApiQuestionMapper mapper = Mappers.getMapper(QuizApiQuestionMapper.class);

    @Test
    void shouldConvertToQuestion() {
        Question expectedResult = Question.builder()
                .question(QUIZ_API_QUESTION.question())
                .answers(new HashSet<>(QUIZ_API_QUESTION.answers().values()))
                .correctAnswers(Set.of("Shut down the system"))
                .category(QUIZ_API_QUESTION.category())
                .multiplyAnswers(true)
                .difficulty(QUIZ_API_QUESTION.difficulty())
                .build();

        Question result = mapper.toQuestion(QUIZ_API_QUESTION);
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}