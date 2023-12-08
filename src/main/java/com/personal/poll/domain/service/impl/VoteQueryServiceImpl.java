package com.personal.poll.domain.service.impl;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.service.IVoteQueryService;
import com.personal.poll.domain.service.IVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteQueryServiceImpl  implements IVoteQueryService {

    private final IVoteService voteService;

    @Override
    public VoteConfirmationDTO find(Long id) {
        return new VoteConfirmationDTO(voteService.find(id));
    }
}
