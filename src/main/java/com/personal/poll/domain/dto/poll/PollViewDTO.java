package com.personal.poll.domain.dto.poll;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.PollEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PollViewDTO {
    private Long id;

    private String subject;

    private PollStatusEnum status;

    private VoteValueEnum winner;

    private Long totalVotes;

    public PollViewDTO(PollEntity pollEntity) {
        this.id = pollEntity.getId();
        this.subject = pollEntity.getSubject();
        this.status = pollEntity.getStatus();
        this.winner = pollEntity.getWinningVote();
        this.totalVotes = PollStatusEnum.CLOSED.equals(pollEntity.getStatus()) ?
                pollEntity.getTotalPositiveVotes() + pollEntity.getTotalNegativeVotes() :
                null;
    }
}
