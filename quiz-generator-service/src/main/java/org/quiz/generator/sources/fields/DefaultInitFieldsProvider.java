package org.quiz.generator.sources.fields;

import org.quizstorage.generator.dto.InitField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class DefaultInitFieldsProvider implements InitFieldsProvider {

    private final List<InitFieldProvider> initFieldProviders;

    public DefaultInitFieldsProvider(Collection<? extends InitFieldProvider> initFieldProviders) {
        this.initFieldProviders = new ArrayList<>(initFieldProviders);
    }

    @Override
    public Stream<InitField> getInitFields() {
        return initFieldProviders.stream().map(InitFieldProvider::get);
    }

}
