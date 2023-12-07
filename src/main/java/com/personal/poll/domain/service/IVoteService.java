package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;

public interface IVoteService {

    VoteConfirmationDTO registerVote(VoteRegistryDTO voteRegistry);
}
