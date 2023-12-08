package com.personal.poll.domain.fixture.poll.models;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.util.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class PollEntityFixture {

    public static PollEntity randomPending() {
        return PollEntity.builder()
                .subject(RandomStringUtils.randomAlphabetic(1, 60))
                .status(PollStatusEnum.PENDING)
                .id(RandomUtils.random.nextLong())
                .build();
    }

    public static PollEntity randomOpen() {
        return PollEntity.builder()
                .subject(RandomStringUtils.randomAlphabetic(1, 60))
                .status(PollStatusEnum.OPEN)
                .totalPositiveVotes(RandomUtils.random.nextLong(0, 10))
                .totalNegativeVotes(RandomUtils.random.nextLong(0, 10))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusSeconds(RandomUtils.random.nextLong(0, 1800)))
                .id(RandomUtils.random.nextLong())
                .build();
    }

    public static PollEntity randomOpenReadyToClose() {
        return PollEntity.builder()
                .subject(RandomStringUtils.randomAlphabetic(1, 60))
                .status(PollStatusEnum.OPEN)
                .totalPositiveVotes(RandomUtils.random.nextLong(0, 10))
                .totalNegativeVotes(RandomUtils.random.nextLong(0, 10))
                .startTime(LocalDateTime.now().minusSeconds(RandomUtils.random.nextLong(1, 1800)))
                .endTime(LocalDateTime.now().minusSeconds(1))
                .id(RandomUtils.random.nextLong())
                .build();
    }
}
