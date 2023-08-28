package org.quiz.generator.sources.opentriviadb;

import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionCategoryResponse;
import org.quiz.generator.sources.opentriviadb.dto.TriviaQuestionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "triviaCategoriesProvider", url = "${quiz-sources.trivia.url}")
public interface TriviaClient {


    @GetMapping(value = "${quiz-sources.trivia.categories-list-path}")
    TriviaQuestionCategoryResponse cagetories();

    @GetMapping(value = "${quiz-sources.trivia.questions-path}")
    TriviaQuestionResponse questions(@RequestParam Map<String, String> queryParams);

}
