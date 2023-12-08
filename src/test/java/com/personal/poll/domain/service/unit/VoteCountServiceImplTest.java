package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.fixture.member.models.MemberEntityFixture;
import com.personal.poll.domain.fixture.poll.models.PollEntityFixture;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.repository.IPollRepository;
import com.personal.poll.domain.service.implementation.VoteCountServiceImpl;
import com.personal.poll.util.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VoteCountServiceImplTest {

    @Mock
    private IPollRepository pollRepository;

    @InjectMocks
    private VoteCountServiceImpl voteCountService;

    public static final long TEST_COUNT = 30L;

    @Test
    void shouldCountVotesOfPollWithYesWinning() {
        List<MemberEntity> voters = Stream
                .generate(MemberEntityFixture::random)
                .limit(TEST_COUNT)
                .toList();
        long positiveVotesCount = RandomUtils.random.nextLong(TEST_COUNT/2 + 1,TEST_COUNT);
        long negativeVotesCount = TEST_COUNT - positiveVotesCount;
        PollEntity agenda = PollEntityFixture.randomOpenReadyToClose();

        agenda.setTotalPositiveVotes(positiveVotesCount);
        agenda.setTotalNegativeVotes(negativeVotesCount);

        when(pollRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));
        when(pollRepository.save(agenda)).then(AdditionalAnswers.returnsFirstArg());

        voteCountService.countVotesOfPoll(agenda.getId());

        assertEquals(PollStatusEnum.CLOSED, agenda.getStatus());
        assertEquals(VoteValueEnum.YES, agenda.getWinningVote());
    }

    @Test
    void shouldCountVotesOfPollWithNoWinning() {
        List<MemberEntity> voters = Stream
                .generate(MemberEntityFixture::random)
                .limit(TEST_COUNT)
                .toList();
        long negativeVotesCount = RandomUtils.random.nextLong(TEST_COUNT/2 + 1,TEST_COUNT);
        long positiveVotesCount = TEST_COUNT - negativeVotesCount;
        PollEntity agenda = PollEntityFixture.randomOpenReadyToClose();

        agenda.setTotalPositiveVotes(positiveVotesCount);
        agenda.setTotalNegativeVotes(negativeVotesCount);

        when(pollRepository.findById(agenda.getId())).thenReturn(Optional.of(agenda));
        when(pollRepository.save(agenda)).then(AdditionalAnswers.returnsFirstArg());

        voteCountService.countVotesOfPoll(agenda.getId());

        assertEquals(PollStatusEnum.CLOSED, agenda.getStatus());
        assertEquals(VoteValueEnum.NO, agenda.getWinningVote());
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenNotFindingAgenda() {
        Long id = RandomUtils.random.nextLong();
        when(pollRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> voteCountService.countVotesOfPoll(id));
    }
}