package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.dto.poll.PollResultDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IVoteCountService;
import com.personal.poll.domain.service.IPollResultMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteCountServiceImpl implements IVoteCountService {

    private final IPollRepository pollRepository;

    private final IPollResultMessageService pollResultMessageService;

    @Override
    public void countVotesOfPoll(Long pollId) {
        var poll = findPoll(pollId);
        if (poll.getStatus().equals(PollStatusEnum.OPEN) && LocalDateTime.now().isAfter(poll.getEndTime())) {
            Long totalVotes = poll.getTotalPositiveVotes() + poll.getTotalNegativeVotes();
            startVoteCount(poll);
            log.info("Agenda: {}, Winning vote: {}, Total votes {}",
                    poll.getSubject(), poll.getWinningVote(), totalVotes);
            pollResultMessageService.send(new PollResultDTO(poll));
            pollRepository.save(poll);
        }
    }

    private PollEntity findPoll(Long pollId) {
        return pollRepository.findPollWithVotesById(pollId).orElseThrow(() -> {
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
