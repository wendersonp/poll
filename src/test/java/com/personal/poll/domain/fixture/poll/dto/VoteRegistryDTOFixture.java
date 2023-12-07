package com.personal.poll.domain.fixture.poll.dto;

import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.enums.VoteValueEnum;

public class VoteRegistryDTOFixture {

    public static VoteRegistryDTO generatePositive(Long voterId, Long agendaId) {
        return new VoteRegistryDTO(VoteValueEnum.YES, voterId, agendaId);
    }

    public static VoteRegistryDTO generateNegative(Long voterId, Long agendaId) {
        return new VoteRegistryDTO(VoteValueEnum.NO, voterId, agendaId);
    }
}
