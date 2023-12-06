package com.personal.poll.domain.dto.member;

import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.util.ValidationMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Valid
public class MemberCreateDTO {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Size(max = 40, message = ValidationMessages.SIZE_LESS_THAN_40)
    private String name;

    @Pattern(regexp = "\\d{11}", message = ValidationMessages.ONLY_11_DIGITS)
    private String cpfNumber;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .name(name)
                .cpfNumber(cpfNumber)
                .build();
    }
}
