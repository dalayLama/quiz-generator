package org.quiz.generator.sources.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.quiz.generator.exceptions.MissedRequiredInitFieldException;
import org.quizstorage.generator.dto.InitField;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class NormalizeUtil {

    public static <S extends Map<String, String>> Map<String, String> toMap(Collection<InitField> initFields, S from) {
        return initFields.stream()
                .map(initField -> generate(initField, from))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static <S extends Map<String, String>> Map.Entry<String, String> generate(InitField field, S initData) {
        String value = initData.get(field.name());
        boolean blank = StringUtils.isBlank(value);
        if (blank && field.required()) {
            throw new MissedRequiredInitFieldException(field.name());
        } else if (blank) {
            return null;
        }
        return Map.entry(field.name(), value);
    }

}
