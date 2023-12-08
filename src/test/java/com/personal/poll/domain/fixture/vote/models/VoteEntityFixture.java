package com.personal.poll.domain.fixture.vote.models;

import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.fixture.member.models.MemberEntityFixture;
import com.personal.poll.domain.models.PollEntity;
import com.personal.poll.domain.models.VoteEntity;
import com.personal.poll.util.RandomUtils;

public class VoteEntityFixture {

    public static VoteEntity positiveVote(PollEntity agenda) {
        return VoteEntity.builder()
                .id(RandomUtils.random.nextLong())
                .vote(VoteValueEnum.YES)
                .voter(MemberEntityFixture.random())
                .agenda(agenda)
                .build();
    }

    public static VoteEntity negativeVote(PollEntity agenda) {
        return VoteEntity.builder()
                .id(RandomUtils.random.nextLong())
                .vote(VoteValueEnum.NO)
                .voter(MemberEntityFixture.random())
                .agenda(agenda)
                .build();
    }
}
