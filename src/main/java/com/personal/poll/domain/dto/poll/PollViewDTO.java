package com.personal.poll.domain.dto.poll;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollViewDTO {
    private Long id;

    private String subject;

    private PollStatusEnum status;

    public PollViewDTO(PollEntity pollEntity) {
        this.id = pollEntity.getId();
        this.subject = pollEntity.getSubject();
        this.status = pollEntity.getStatus();
    }
}
