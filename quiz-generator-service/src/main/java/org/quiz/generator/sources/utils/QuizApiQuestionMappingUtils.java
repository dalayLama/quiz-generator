package org.quiz.generator.sources.utils;

import lombok.experimental.UtilityClass;
import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;

@UtilityClass
public class QuizApiQuestionMappingUtils {

    public static String extractAnswerKey(String correctAnswerKey) {
        return correctAnswerKey.substring(0, correctAnswerKey.indexOf("_correct"));
    }

    public static String getAnswer(QuizApiQuestion quizApiQuestion, String answerKey) {
        String answer = quizApiQuestion.answers().get(answerKey);
        if (answer == null) {
            String message = "QuizApiQuestion with id %d doesn't contain answer with key %s"
                    .formatted(quizApiQuestion.id(), answerKey);
            throw new IllegalArgumentException(message);
        }
        return answer;
    }

}
