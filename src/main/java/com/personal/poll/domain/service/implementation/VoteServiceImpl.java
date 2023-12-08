package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.exception.ClosedPollException;
import com.personal.poll.domain.exception.PendingPollException;
import com.personal.poll.domain.exception.VoteAlreadyRegisteredException;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.models.VoteEntity;
import com.personal.poll.domain.repository.IVoteRepository;
import com.personal.poll.domain.service.IMemberService;
import com.personal.poll.domain.service.IPollService;
import com.personal.poll.domain.service.IVoteService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements IVoteService {

    private final IVoteRepository voteRepository;

    private final IMemberService voterService;

    private final IPollService agendaService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public VoteConfirmationDTO registerVote(VoteRegistryDTO voteRegistry) {
        MemberEntity voter = voterService.find(voteRegistry.getVoterId());
        PollEntity agenda = agendaService.find(voteRegistry.getAgendaId());

        checkAgendaStatus(agenda);
        VoteEntity vote = persistVote(voteRegistry, agenda, voter);

        agenda.calculateVote(vote.getVote());
        return new VoteConfirmationDTO(vote);
    }

    private void checkAgendaStatus(PollEntity agenda){
        if (agenda.getStatus().equals(PollStatusEnum.PENDING)) {
            throw new PendingPollException();
        }
        if (agenda.getStatus().equals(PollStatusEnum.CLOSED) ||
            LocalDateTime.now().isAfter(agenda.getEndTime())) {
            throw new ClosedPollException();
        }
    }

    private VoteEntity persistVote(VoteRegistryDTO voteRegistry, PollEntity agenda, MemberEntity member) {
        try {
            VoteEntity vote = voteRegistry.toEntity(agenda, member);
            return voteRepository.save(vote);
        } catch (ConstraintViolationException exception) {
            throw new VoteAlreadyRegisteredException();
        }
    }

}
