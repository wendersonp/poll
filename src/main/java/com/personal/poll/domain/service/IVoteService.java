package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.models.VoteEntity;

public interface IVoteService {

    VoteConfirmationDTO registerVote(VoteRegistryDTO voteRegistry);

    VoteEntity find(Long id);
}
