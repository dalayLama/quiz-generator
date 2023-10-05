package org.quiz.generator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.quizstorage.generator.dto.QuizSourceDto;
import org.quiz.generator.exceptions.QuizInitDataConvertingException;
import org.quiz.generator.exceptions.QuizSourceNotFoundException;
import org.quiz.generator.sources.*;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.QuestionSet;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz/sources")
@RequiredArgsConstructor
public class QuizSourceController {

    private final QuizSourcesProvider quizSourcesProvider;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<QuizSourceDto> getSources() {
        return quizSourcesProvider.sources()
                .map(source -> new QuizSourceDto(source.id(), source.name(), source.description()))
                .toList();
    }

    @GetMapping("/{id}/init-data-definition")
    public List<InitField<?>> getInitData(@PathVariable String id) {
        return quizSourcesProvider.getSource(id)
                .map(QuizSource::initFields)
                .orElseThrow(() -> new QuizSourceNotFoundException(id));
    }

    @PostMapping("/{id}/questions")
    public QuestionSet generateQuestions(@PathVariable String id,
                                         @RequestBody byte[] jsonInitData) {
        return quizSourcesProvider.getSource(id)
                .map(source -> generateQuestionSet(jsonInitData, source))
                .orElseThrow(() -> new QuizSourceNotFoundException(id));
    }

    private <T extends InitData> QuestionSet generateQuestionSet(byte[] jsonInitData,
                                                                 QuizSource<T> quizSource) {
        T initData = convertInitData(jsonInitData, quizSource.initDataClass());
        return quizSource.generate(initData);
    }

    private <T> T convertInitData(byte[] json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new QuizInitDataConvertingException(e);
        }
    }

}
