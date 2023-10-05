package org.quizstorage.generator.dto;

public enum FieldType {

    NUMBER, SELECT;

    public <T> InitField<T> createInitField(String name, String description, boolean required, T conf) {
        return new InitField<>(name, description, this, required, conf);
    }

}
