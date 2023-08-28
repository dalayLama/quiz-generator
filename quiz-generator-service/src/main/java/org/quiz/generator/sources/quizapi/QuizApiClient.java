package org.quiz.generator.sources.quizapi;

import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@FeignClient(name = "quizApiClient", url = "${quiz-sources.quiz-api.url}")
public interface QuizApiClient {

    @GetMapping(path = "${quiz-sources.quiz-api.paths.questions}")
    List<QuizApiQuestion> questions(@RequestParam Map<String, String> params);

}
