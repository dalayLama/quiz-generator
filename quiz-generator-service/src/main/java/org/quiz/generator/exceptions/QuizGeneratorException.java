package org.quiz.generator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class QuizGeneratorException extends RuntimeException implements ErrorResponse {

    private final HttpStatus statusCode;

    public QuizGeneratorException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public QuizGeneratorException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public QuizGeneratorException(String message, Throwable cause, HttpStatus statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public QuizGeneratorException(Throwable cause, HttpStatus statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public QuizGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public ProblemDetail getBody() {
        return ProblemDetail.forStatus(statusCode);
    }

}
