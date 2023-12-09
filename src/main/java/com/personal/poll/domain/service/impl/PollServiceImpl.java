package com.personal.poll.domain.service.impl;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollStartDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.exception.PollNotPendingException;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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
    public PollStartDTO start(Long id, Long durationInSeconds) {
        PollEntity poll = find(id);
        validatePollBeforeStart(poll);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = calculateVotingEndingTime(startTime, durationInSeconds);

        poll.openVoting(startTime, endTime);
        pollRepository.save(poll);
        scheduler.schedule(() -> voteCountService.countVotesOfPoll(id),
                Instant.from(endTime
                        .plusSeconds(VOTE_COUNT_GAP)
                        .atZone(ZoneId.systemDefault())
                )
        );

        return new PollStartDTO(poll);
    }

    @Override
    public PollEntity find(Long id) {
        return pollRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.POLL_NOT_FOUND));
    }

    @Override
    public List<PollEntity> findAll() {
        return pollRepository.findAll();
    }

    private LocalDateTime calculateVotingEndingTime(LocalDateTime startTime, Long durationInSeconds) {
        return startTime.plusSeconds(durationInSeconds);
    }

    private void validatePollBeforeStart(PollEntity poll) {
        if (PollStatusEnum.PENDING != poll.getStatus()) {
            throw new PollNotPendingException();
        }
    }
}
