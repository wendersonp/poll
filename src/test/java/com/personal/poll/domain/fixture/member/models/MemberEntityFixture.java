package com.personal.poll.domain.fixture.member.models;

import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.util.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class MemberEntityFixture {

    public static MemberEntity random() {
        return MemberEntity.builder()
                .name(RandomStringUtils.randomAlphabetic(1, 40))
                .cpfNumber(RandomStringUtils.randomNumeric(11))
                .id(RandomUtils.random.nextLong(1, 1000))
                .build();
    }
}
