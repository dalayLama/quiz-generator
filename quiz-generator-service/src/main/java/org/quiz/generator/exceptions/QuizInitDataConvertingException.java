package org.quiz.generator.exceptions;

import org.springframework.http.HttpStatus;

public class QuizInitDataConvertingException extends QuizGeneratorException {
    public QuizInitDataConvertingException(Throwable e) {
        super(e, HttpStatus.BAD_REQUEST);
    }
}
