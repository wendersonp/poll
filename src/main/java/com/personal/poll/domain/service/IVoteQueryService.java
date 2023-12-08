package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;

public interface IVoteQueryService {

    VoteConfirmationDTO find(Long id);
}
