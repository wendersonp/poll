package com.personal.poll.domain.config;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IVoteCountService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Configuration
@DependsOn({"IPollRepository", "voteCountServiceImpl", "taskScheduler"})
@RequiredArgsConstructor
public class VoteCountSyncConfiguration {

    private static final long VOTE_COUNT_GAP = 2;

    private final IPollRepository pollRepository;

    private final IVoteCountService voteCountService;

    private final TaskScheduler taskScheduler;

    @PostConstruct
    void synchronizeCountingSchedule() {
        List<PollEntity> openAgendas = pollRepository.findByStatus(PollStatusEnum.OPEN);
        openAgendas.forEach(this::scheduleAgenda);
    }

    private void scheduleAgenda(PollEntity agenda) {
        Long agendaId = agenda.getId();
        taskScheduler.schedule(() -> voteCountService.countVotesOfPoll(agendaId),
                Instant.from(agenda
                        .getEndTime()
                        .plusSeconds(VOTE_COUNT_GAP)
                        .atZone(ZoneId.systemDefault())
                )
        );
    }
}
