package org.quiz.generator.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.quizstorage.generator.dto.Question;
import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;
import org.quiz.generator.sources.utils.QuizApiQuestionMappingUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuizApiQuestionMapper {

    @Mapping(target = "question", source = "question")
    @Mapping(target = "answers", source = "quizApiQuestion", qualifiedByName = "createAnswers")
    @Mapping(target = "correctAnswers", source = "quizApiQuestion", qualifiedByName = "createCorrectAnswers")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "multiplyAnswers", constant = "true")
    @Mapping(target = "difficulty", source = "difficulty")
    Question toQuestion(QuizApiQuestion quizApiQuestion);

    @Named("createAnswers")
    default Set<String> createAnswers(QuizApiQuestion quizApiQuestion) {
        return quizApiQuestion.answers().values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Named("createCorrectAnswers")
    default Set<String> createCorrectAnswers(QuizApiQuestion quizApiQuestion) {
        Set<String> collect = quizApiQuestion.correctAnswers().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> QuizApiQuestionMappingUtils.extractAnswerKey(entry.getKey()))
                .map(answerKey -> QuizApiQuestionMappingUtils.getAnswer(quizApiQuestion, answerKey))
                .collect(Collectors.toSet());
        if (collect.isEmpty()) {
            System.out.println("question doesn't have correct answers: %s".formatted(quizApiQuestion));
        }
        return collect;
    }

}
