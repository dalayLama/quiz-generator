package org.quiz.generator.sources.quizapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.quizstorage.generator.dto.Difficulty;

import java.util.Map;
import java.util.Set;

@Builder
public record QuizApiQuestion(
        long id,
        String question,
        String description,
        Map<String, String> answers,
        @JsonProperty("multiply_correct_answers")
        boolean multiplyCorrectAnswers,
        @JsonProperty("correct_answers")
        Map<String, Boolean> correctAnswers,
        @JsonProperty("correct_answer")
        String correctAnswer,
        String explanation,
        String tip,
        Set<QuizApiTag> tags,
        String category,
        Difficulty difficulty
) {
}
