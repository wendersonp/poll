package com.personal.poll.domain.enums;

import lombok.Getter;

@Getter
public enum VoteValueEnum {
    YES("Y"),
    NO("N");

    private final String code;

    VoteValueEnum(String code) {
        this.code = code;
    }
}
