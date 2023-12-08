package com.personal.poll.infrastructure.converters;


import com.personal.poll.domain.enums.VoteValueEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class VoteValueEnumConverter implements AttributeConverter<VoteValueEnum, String> {
    @Override
    public String convertToDatabaseColumn(VoteValueEnum voteValueEnum) {
        if (voteValueEnum == null) {
            return null;
        }
        return voteValueEnum.getCode();
    }

    @Override
    public VoteValueEnum convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Arrays.stream(VoteValueEnum.values())
                .filter(it -> s.equals(it.getCode()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
