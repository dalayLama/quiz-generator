package org.quiz.generator.sources.opentriviadb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.quizstorage.generator.dto.Difficulty;

import java.util.Set;

@Builder
public record TriviaQuestion(
        String category,
        TriviaQuestionType type,
        Difficulty difficulty,
        String question,
        @JsonProperty("correct_answer")
        String correctAnswer,
        @JsonProperty("incorrect_answers")
        Set<String> incorrectAnswers
) {
}
