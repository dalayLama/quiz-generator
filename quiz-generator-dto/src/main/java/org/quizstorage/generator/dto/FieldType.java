package org.quizstorage.generator.dto;

public enum FieldType {

    NUMBER, SELECT;

    public InitField createInitField(String name, String description, boolean required, Object conf) {
        return new InitField(name, description, this, required, conf);
    }

}
