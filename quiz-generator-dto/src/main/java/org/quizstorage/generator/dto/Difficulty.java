package org.quizstorage.generator.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Difficulty {

    EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

    private final String description;

}
