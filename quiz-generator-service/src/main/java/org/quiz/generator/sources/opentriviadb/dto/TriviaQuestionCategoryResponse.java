package org.quiz.generator.sources.opentriviadb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TriviaQuestionCategoryResponse(
        @JsonProperty("trivia_categories")
        List<TriviaQuestionCategory> categories
) {
}
