package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.fixture.poll.dto.PollCreateDTOFixture;
import com.personal.poll.domain.fixture.poll.models.PollEntityFixture;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.IVoteCountService;
import com.personal.poll.domain.service.implementation.PollServiceImpl;
import com.personal.poll.util.AssertUtils;
import com.personal.poll.util.RandomUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PollServiceImplTest {

    @Mock
    private IPollRepository pollRepository;

    @Mock
    private IVoteCountService voteCountService;

    @Mock
    private TaskScheduler scheduler;

    @InjectMocks
    private PollServiceImpl service;

    private static PollCreateDTO pollCreateDTO;

    @Test
    void shouldCreatePollSuccessfully() {
        pollCreateDTO = PollCreateDTOFixture.random();

        when(pollRepository.save(any())).thenAnswer(this::setIdAndReturnsFirstArg);

        PollViewDTO pollViewDTO = service.create(pollCreateDTO);

        assertEquals(pollCreateDTO.getSubject(), pollViewDTO.getSubject());
        assertNotNull(pollViewDTO.getId());
        assertEquals(PollStatusEnum.PENDING, pollViewDTO.getStatus());
    }

    @Test
    void shouldStartVotingSuccessfully() {
        PollEntity agenda = PollEntityFixture.randomPending(RandomUtils.random.nextLong());
        long duration = RandomUtils.random.nextLong(0, 500);

        when(pollRepository.findById(any())).thenReturn(Optional.of(agenda));
        when(pollRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        service.start(agenda.getId(), duration);

        assertNotNull(agenda.getStartTime());
        assertNotNull(agenda.getEndTime());
        assertEquals(agenda.getStartTime().plusSeconds(duration), agenda.getEndTime());
        assertEquals(PollStatusEnum.OPEN, agenda.getStatus());
        verify(scheduler, times(1))
                .scheduleWithFixedDelay(any(Runnable.class), any(Duration.class));
    }

    @Test
    void shouldFindPollSuccessfully() {
        PollEntity agenda = PollEntityFixture.randomPending(RandomUtils.random.nextLong());
        when(pollRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));

        var foundAgenda = service.find(agenda.getId());

        AssertUtils.assertAllFields(agenda, foundAgenda);
    }

    @Test
    void shouldThrowEntityNotFoundWhenFindPoll() {
        Long id = RandomUtils.random.nextLong();
        when(pollRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.find(id));
    }

    private PollEntity setIdAndReturnsFirstArg(InvocationOnMock invocationOnMock) {
        PollEntity object = invocationOnMock.getArgument(0);

        assertEquals(0L, object.getTotalPositiveVotes());
        assertEquals(0L, object.getTotalNegativeVotes());
        assertNull(object.getWinningVote());
        assertNull(object.getStartTime());
        assertNull(object.getEndTime());

        object.setId(RandomUtils.random.nextLong());
        return object;
    }
}