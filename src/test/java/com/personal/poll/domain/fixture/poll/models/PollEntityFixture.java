package com.personal.poll.domain.fixture.poll.models;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.util.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class PollEntityFixture {

    public static PollEntity randomPending(Long id) {
        return PollEntity.builder()
                .subject(RandomStringUtils.randomAlphabetic(1, 60))
                .status(PollStatusEnum.PENDING)
                .id(id)
                .build();
    }

    public static PollEntity randomOpen(Long id) {
        return PollEntity.builder()
                .subject(RandomStringUtils.randomAlphabetic(1, 60))
                .status(PollStatusEnum.OPEN)
                .totalPositiveVotes(RandomUtils.random.nextLong(0, 10))
                .totalNegativeVotes(RandomUtils.random.nextLong(0, 10))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusMinutes(RandomUtils.random.nextLong(0, 300)))
                .id(id)
                .build();
    }
}
