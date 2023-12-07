package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IPollService;
import com.personal.poll.domain.service.IVoteCountService;
import com.personal.poll.util.ExceptionMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements IPollService {

    private final IPollRepository pollRepository;

    private final IVoteCountService voteCountService;

    private final TaskScheduler scheduler;

    private static final long VOTE_COUNT_GAP = 2L;

    @Override
    public PollViewDTO create(PollCreateDTO pollCreate) {
        PollEntity poll = pollCreate.toEntity();
        return new PollViewDTO(pollRepository.save(poll));
    }

    @Override
    public void start(Long id, Long durationInSeconds) {
        PollEntity poll = find(id);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = calculateVotingEndingTime(startTime, durationInSeconds);

        poll.openVoting(startTime, endTime);
        pollRepository.save(poll);
        scheduler.scheduleWithFixedDelay(() -> voteCountService.countVotesOfPoll(id),
                Duration.ofSeconds(durationInSeconds + VOTE_COUNT_GAP));
    }

    @Override
    public PollEntity find(Long id) {
        return pollRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.POLL_NOT_FOUND));
    }

    private LocalDateTime calculateVotingEndingTime(LocalDateTime startTime, Long durationInSeconds) {
        return startTime.plusSeconds(durationInSeconds);
    }
}
