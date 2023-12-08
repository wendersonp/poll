package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollStartDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.models.PollEntity;

public interface IPollService {

    PollViewDTO create(PollCreateDTO pollCreate);

    PollStartDTO start(Long id, Long durationInSeconds);

    PollEntity find(Long id);
}
