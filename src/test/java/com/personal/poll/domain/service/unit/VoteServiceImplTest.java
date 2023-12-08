package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.exception.ClosedPollException;
import com.personal.poll.domain.exception.PendingPollException;
import com.personal.poll.domain.exception.VoteAlreadyRegisteredException;
import com.personal.poll.domain.fixture.vote.dto.VoteRegistryDTOFixture;
import com.personal.poll.domain.fixture.member.models.MemberEntityFixture;
import com.personal.poll.domain.fixture.poll.models.PollEntityFixture;
import com.personal.poll.domain.models.VoteEntity;
import com.personal.poll.domain.repository.IVoteRepository;
import com.personal.poll.domain.service.IMemberService;
import com.personal.poll.domain.service.IPollService;
import com.personal.poll.domain.service.impl.VoteServiceImpl;
import com.personal.poll.util.AssertUtils;
import com.personal.poll.util.RandomUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    @Mock
    private IVoteRepository repository;

    @Mock
    private IMemberService voterService;

    @Mock
    private IPollService agendaService;

    @InjectMocks
    private VoteServiceImpl service;

    @ParameterizedTest
    @EnumSource(VoteValueEnum.class)
    void shouldRegisterVoteSuccessfully(VoteValueEnum voteValue){
        var voter = MemberEntityFixture.random();
        var agenda = PollEntityFixture.randomOpen();
        var vote = new VoteRegistryDTO(voteValue, voter.getId(), agenda.getId());
        var countBeforeVote = voteValue.equals(VoteValueEnum.YES) ? agenda.getTotalPositiveVotes() : agenda.getTotalNegativeVotes();

        when(voterService.find(voter.getId())).thenReturn(voter);
        when(agendaService.find(agenda.getId())).thenReturn(agenda);
        when(repository.save(any())).then(invocationOnMock -> {
            VoteEntity savedVote = invocationOnMock.getArgument(0);
            savedVote.setId(RandomUtils.random.nextLong());
            return savedVote;
        });

        var voteConfirmation = service.registerVote(vote);

        if (VoteValueEnum.YES.equals(voteValue)) {
            assertEquals(countBeforeVote + 1, agenda.getTotalPositiveVotes());
        } else {
            assertEquals(countBeforeVote + 1, agenda.getTotalNegativeVotes());
        }

        assertEquals(voteValue, voteConfirmation.getVote());
        AssertUtils.assertAllFields(new MemberViewDTO(voter), voteConfirmation.getVoter());
        AssertUtils.assertAllFields(new PollViewDTO(agenda), voteConfirmation.getAgenda());
        assertNotNull(voteConfirmation.getId());
    }

    @Test
    void shouldThrowPendingPollExceptionWhenTryToRegisterVoteOnPendingPoll() {
        var voter = MemberEntityFixture.random();
        var agenda = PollEntityFixture.randomPending();
        var vote = VoteRegistryDTOFixture.generatePositive(voter.getId(), agenda.getId());

        when(voterService.find(voter.getId())).thenReturn(voter);
        when(agendaService.find(agenda.getId())).thenReturn(agenda);

        assertThrows(PendingPollException.class,() -> service.registerVote(vote));
    }

    @ParameterizedTest
    @MethodSource(value = "provideArgumentsForClosedPollExceptionTest")
    void shouldThrowClosedPollExceptionWhenTryingToRegisterVoteOnClosedPoll(PollStatusEnum status, boolean shouldTestRegisterVoteAfterEndTime) {
        var voter = MemberEntityFixture.random();
        var agenda = PollEntityFixture.randomOpen();
        var vote = VoteRegistryDTOFixture.generatePositive(voter.getId(), agenda.getId());
        agenda.setStatus(status);
        if (shouldTestRegisterVoteAfterEndTime) {
            agenda.setEndTime(agenda.getStartTime().minusSeconds(1));
            agenda.setStartTime(agenda.getStartTime().minusSeconds(2));
        }

        when(voterService.find(voter.getId())).thenReturn(voter);
        when(agendaService.find(agenda.getId())).thenReturn(agenda);

        assertThrows(ClosedPollException.class, () -> service.registerVote(vote));
    }

    @Test
    void shouldThrowVoteAlreadyRegisteredExceptionWhenTryingToSaveVote() {
        var voter = MemberEntityFixture.random();
        var agenda = PollEntityFixture.randomOpen();
        var vote = VoteRegistryDTOFixture.generatePositive(voter.getId(), agenda.getId());

        when(voterService.find(voter.getId())).thenReturn(voter);
        when(agendaService.find(agenda.getId())).thenReturn(agenda);
        when(repository.save(any())).thenThrow(ConstraintViolationException.class);

        assertThrows(VoteAlreadyRegisteredException.class, () -> service.registerVote(vote));

    }

    private static Stream<Arguments> provideArgumentsForClosedPollExceptionTest() {
        return Stream.of(
                Arguments.of(PollStatusEnum.CLOSED, false),
                Arguments.of(PollStatusEnum.OPEN, true)
        );
    }
}