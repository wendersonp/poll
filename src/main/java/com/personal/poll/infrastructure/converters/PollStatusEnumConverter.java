package com.personal.poll.infrastructure.converters;

import com.personal.poll.domain.enums.PollStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class PollStatusEnumConverter implements AttributeConverter<PollStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(PollStatusEnum pollStatusEnum) {
        if (pollStatusEnum == null) {
            return null;
        }
        return pollStatusEnum.getCode();
    }

    @Override
    public PollStatusEnum convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Arrays.stream(PollStatusEnum.values())
                .filter(it -> s.equals(it.getCode()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
