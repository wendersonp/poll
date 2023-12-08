package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollResultDTO;

public interface IPollResultMessageService {

    void send(PollResultDTO pollResultDTO);
}
