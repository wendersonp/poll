package com.personal.poll.domain.dto.poll;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollStartDTO {
    private Long id;

    private String subject;

    private PollStatusEnum status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    public PollStartDTO(PollEntity pollEntity) {
        this.id = pollEntity.getId();
        this.subject = pollEntity.getSubject();
        this.status = pollEntity.getStatus();
        this.startTime = pollEntity.getStartTime();
        this.endTime = pollEntity.getEndTime();
    }
}
