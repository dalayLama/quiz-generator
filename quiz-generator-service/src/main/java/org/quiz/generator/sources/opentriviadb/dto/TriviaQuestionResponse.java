package org.quiz.generator.sources.opentriviadb.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TriviaQuestionResponse(
        int responseCode,
        List<TriviaQuestion> results
) {
}
