package org.quiz.generator.exceptions;

import org.springframework.http.HttpStatus;

public class MissedRequiredInitFieldException extends QuizGeneratorException {

    private static final String MESSAGE_TEMPLATE = "Required field %s is absent";

    public MissedRequiredInitFieldException(String fieldName) {
        super(MESSAGE_TEMPLATE.formatted(fieldName), HttpStatus.BAD_REQUEST);
    }

}
