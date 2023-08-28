package org.quiz.generator.exceptions;

public class QuizSourceNotFoundException extends NotFoundException {

    private static final String MESSAGE_TEMPLATE = "Quiz source hasn't been found by id %s";

    public QuizSourceNotFoundException(String id) {
        super(MESSAGE_TEMPLATE.formatted(id));
    }

}
