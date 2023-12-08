package com.personal.poll.domain.dto.vote;

import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.models.VoteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteRegistryDTO {

    private VoteValueEnum vote;

    private Long voterId;

    private Long agendaId;

    public VoteEntity toEntity(PollEntity agenda, MemberEntity voter) {
        return VoteEntity.builder()
                .voter(voter)
                .agenda(agenda)
                .vote(vote)
                .build();
    }
}
