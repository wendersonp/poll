package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;

public interface IPollService {

    PollViewDTO create(PollCreateDTO pollCreate);

    void start(Long id, Long durationInSeconds);
}
