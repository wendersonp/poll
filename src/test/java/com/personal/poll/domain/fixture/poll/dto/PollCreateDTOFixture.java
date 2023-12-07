package com.personal.poll.domain.fixture.poll.dto;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class PollCreateDTOFixture {

    public static PollCreateDTO random() {
        return new PollCreateDTO(RandomStringUtils.randomAlphabetic(1, 60));
    }
}
