package com.personal.poll.domain.dto.poll;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.util.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PollCreateDTO {

    @NotEmpty(message = ValidationMessages.NOT_EMPTY)
    @Size(max = 60, message = ValidationMessages.SIZE_LESS_THAN_60)
    private String subject;

    public PollEntity toEntity() {
        return PollEntity.builder()
                .subject(subject)
                .status(PollStatusEnum.PENDING)
                .build();
    }
}
