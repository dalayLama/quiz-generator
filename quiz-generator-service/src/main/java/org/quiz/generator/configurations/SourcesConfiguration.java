package org.quiz.generator.configurations;

import org.quiz.generator.sources.InitData;
import org.quiz.generator.sources.QuizSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SourcesConfiguration {

    @Bean
    public Map<String, QuizSource<InitData>> sourceMap(Collection<QuizSource<? extends InitData>> sources) {
        return sources.stream()
                .collect(Collectors.toMap(QuizSource::id, o -> (QuizSource<InitData>) o));

    }

}
