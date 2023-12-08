package com.personal.poll.domain.dto.vote;

import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.VoteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteReportDTO {

    private String voterName;

    private VoteValueEnum vote;

    public VoteReportDTO(VoteEntity voteEntity) {
        this.voterName = voteEntity.getVoter().getName();
        this.vote = voteEntity.getVote();
    }
}
