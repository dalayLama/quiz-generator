package org.quiz.generator.mappers;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.quizstorage.generator.dto.Question;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestion;
import org.springframework.web.util.HtmlUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TriviaQuestionMapper {

    @Mapping(target = "question", source = "question", qualifiedByName = "htmlUnescape")
    @Mapping(target = "answers", source = "triviaQuestion", qualifiedByName = "createAnswers")
    @Mapping(target = "correctAnswers", source = "triviaQuestion.correctAnswer", qualifiedByName = "createCorrectAnswers")
    @Mapping(target = "category", source = "category", qualifiedByName = "htmlUnescape")
    @Mapping(target = "multiplyAnswers", constant = "false")
    @Mapping(target = "difficulty", source = "difficulty")
    Question toQuestion(TriviaQuestion triviaQuestion);

    @Named("createAnswers")
    default Set<String> createAnswers(TriviaQuestion triviaQuestion) {
        return Stream.concat(triviaQuestion.incorrectAnswers().stream(), Stream.of(triviaQuestion.correctAnswer()))
                .map(this::htmlUnescape)
                .collect(Collectors.toSet());
    }

    @Named("createCorrectAnswers")
    default Set<String> createCorrectAnswers(String answer) {
        return Set.of(htmlUnescape(answer));
    }

    @Named("htmlUnescape")
    default String htmlUnescape(String string) {
        return HtmlUtils.htmlUnescape(string).trim();
    }

}
