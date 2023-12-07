package com.personal.poll.domain.dto.vote;

import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.VoteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteConfirmationDTO {
    private Long id;

    private VoteValueEnum vote;

    private PollViewDTO agenda;

    private MemberViewDTO voter;

    public VoteConfirmationDTO(VoteEntity voteEntity) {
        this.id = voteEntity.getId();
        this.vote = voteEntity.getVote();
        this.agenda = new PollViewDTO(voteEntity.getAgenda());
        this.voter = new MemberViewDTO(voteEntity.getVoter());
    }
}
