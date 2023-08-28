package org.quiz.generator.sources.quizapi;

import lombok.RequiredArgsConstructor;
import org.quiz.generator.mappers.QuizApiQuestionMapper;
import org.quiz.generator.sources.*;
import org.quiz.generator.sources.fields.InitFieldsProvider;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.Question;
import org.quizstorage.generator.dto.QuestionSet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@ConditionalOnProperty(prefix = "quiz-sources.quiz-api", name = "enable", havingValue = "true")
@Service
@RequiredArgsConstructor
public class QuizApiSource implements QuizSource<QuizApiInitData> {

    private final InitFieldsProvider quizApiInitFieldsProvider;

    private final InitDataNormalizer<QuizApiInitData, Map<String, String>> initDataNormalizer;

    private final QuizApiClient quizApiClient;

    private final QuizApiQuestionMapper questionMapper;

    @Override
    public String id() {
        return "quizapi.io";
    }

    @Override
    public String name() {
        return "QuizApi";
    }

    @Override
    public String description() {
        return "The QuizAPI includes a wide number of Tech questions";
    }

    @Override
    public List<InitField> initFields() {
        return quizApiInitFieldsProvider.getInitFields().toList();
    }

    @Override
    public QuestionSet generate(QuizApiInitData initData) {
        List<InitField> initFields = initFields();
        Map<String, String> params = initDataNormalizer.normalize(initFields, initData);
        List<Question> questions = quizApiClient.questions(params).stream().map(questionMapper::toQuestion).toList();
        return new QuestionSet(id(), questions);
    }

    @Override
    public Class<QuizApiInitData> initDataClass() {
        return QuizApiInitData.class;
    }

}
