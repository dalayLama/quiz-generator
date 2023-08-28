package org.quiz.generator.sources.opentriviadb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TriviaQuestionType {

    MULTIPLE("Multiply choice"),
    BOOLEAN("True / False");

    private final String description;

}
