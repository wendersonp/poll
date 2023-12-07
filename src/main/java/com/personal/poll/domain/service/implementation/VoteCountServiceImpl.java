package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IVoteCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteCountServiceImpl implements IVoteCountService {

    private final IPollRepository pollRepository;

    @Override
    public void countVotesOfPoll(Long pollId) {
        var poll = findPoll(pollId);
        if (poll.getStatus().equals(PollStatusEnum.OPEN) && LocalDateTime.now().isAfter(poll.getEndTime())) {
            Long totalVotes = poll.getTotalPositiveVotes() + poll.getTotalNegativeVotes();
            startVoteCount(poll);
            pollRepository.save(poll);
            log.info("Agenda: {}, Winning vote: {}, Total votes {}",
                    poll.getSubject(), poll.getWinningVote(), totalVotes);
            //TODO: enviar mensagem do resultado para a fila
            //TODO: lembrar de criar verificação para reagendar contagens caso o serviço caia
        }
    }

    private PollEntity findPoll(Long pollId) {
        return pollRepository.findById(pollId).orElseThrow(() -> {
            log.error("Could not find poll of id {} to count", pollId);
            return new IllegalStateException();
        });
    }

    private void startVoteCount(PollEntity poll) {
        poll.setStatus(PollStatusEnum.CLOSED);
        if (poll.getTotalPositiveVotes() > poll.getTotalNegativeVotes()) {
            poll.setWinningVote(VoteValueEnum.YES);
        } else {
            poll.setWinningVote(VoteValueEnum.NO);
        }
    }
}
