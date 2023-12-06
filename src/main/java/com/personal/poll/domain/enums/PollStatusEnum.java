package com.personal.poll.domain.enums;

import lombok.Getter;

@Getter
public enum PollStatusEnum {
    PENDING("P"),
    OPEN("O"),
    CLOSED("C");

    private final String code;

    PollStatusEnum(String code) {
        this.code = code;
    }
}
