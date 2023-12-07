package com.personal.poll.domain.fixture.member.dto;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class MemberCreateDTOFixture {

    public static MemberCreateDTO random() {
        return new MemberCreateDTO(
                RandomStringUtils.randomAlphabetic(1, 40),
                RandomStringUtils.randomNumeric(11)
        );
    }
}
