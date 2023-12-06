package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IPollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements IPollService {

    private final IPollRepository pollRepository;

    @Override
    public PollViewDTO create(PollCreateDTO pollCreate) {
        PollEntity poll = pollCreate.toEntity();
        return new PollViewDTO(poll);
    }

    @Override
    public void start(Long id, Long durationInSeconds) {
        PollEntity poll = pollRepository.getReferenceById(id);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = calculateVotingEndingTime(startTime, durationInSeconds);

        poll.openVoting(startTime, endTime);
        //TODO: Chamar o executor para terminar a votação no horario agendado
    }

    private LocalDateTime calculateVotingEndingTime(LocalDateTime startTime, Long durationInSeconds) {
        return startTime.plusSeconds(durationInSeconds);
    }
}
