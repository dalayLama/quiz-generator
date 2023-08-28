package org.quiz.generator.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends QuizGeneratorException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
