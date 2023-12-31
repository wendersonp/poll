package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.exception.PollNotPendingException;
import com.personal.poll.domain.fixture.poll.dto.PollCreateDTOFixture;
import com.personal.poll.domain.fixture.poll.models.PollEntityFixture;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.impl.PollServiceImpl;
import com.personal.poll.util.AssertUtils;
import com.personal.poll.util.RandomUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PollServiceImplTest {

    @Mock
    private IPollRepository pollRepository;

    @Mock
    private TaskScheduler scheduler;

    @InjectMocks
    private PollServiceImpl service;

    @Test
    void shouldCreatePollSuccessfully() {
        PollCreateDTO pollCreateDTO = PollCreateDTOFixture.random();

        when(pollRepository.save(any())).thenAnswer(this::setIdAndReturnsFirstArg);

        PollViewDTO pollViewDTO = service.create(pollCreateDTO);

        assertEquals(pollCreateDTO.getSubject(), pollViewDTO.getSubject());
        assertNotNull(pollViewDTO.getId());
        assertEquals(PollStatusEnum.PENDING, pollViewDTO.getStatus());
    }

    @Test
    void shouldStartVotingSuccessfully() {
        PollEntity agenda = PollEntityFixture.randomPending();
        long duration = RandomUtils.random.nextLong(0, 500);

        when(pollRepository.findById(any())).thenReturn(Optional.of(agenda));
        when(pollRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        service.start(agenda.getId(), duration);

        assertNotNull(agenda.getStartTime());
        assertNotNull(agenda.getEndTime());
        assertEquals(agenda.getStartTime().plusSeconds(duration), agenda.getEndTime());
        assertEquals(PollStatusEnum.OPEN, agenda.getStatus());
        verify(scheduler, times(1))
                .schedule(any(Runnable.class),(Instant) argThat((Instant instant) -> {
                    LocalDateTime executionTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    return executionTime.isAfter(agenda.getEndTime());
                }));
    }

    @Test
    void shouldThrowPollNotPendingExceptionWhenPollStatusOpen() {
        PollEntity agenda = PollEntityFixture.randomOpen();
        Long agendaId = agenda.getId();
        long duration = RandomUtils.random.nextLong(0, 500);

        when(pollRepository.findById(any())).thenReturn(Optional.of(agenda));
        assertThrows(PollNotPendingException.class, () -> service.start(agendaId, duration));
    }

    @Test
    void shouldThrowPollNotPendingExceptionWhenPollStatusClosed() {
        PollEntity agenda = PollEntityFixture.randomOpen();
        agenda.setStatus(PollStatusEnum.CLOSED);
        long duration = RandomUtils.random.nextLong(0, 500);
        Long agendaId = agenda.getId();

                when(pollRepository.findById(any())).thenReturn(Optional.of(agenda));
        assertThrows(PollNotPendingException.class, () -> service.start(agendaId, duration));
    }

    @Test
    void shouldFindPollSuccessfully() {
        PollEntity agenda = PollEntityFixture.randomPending();
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