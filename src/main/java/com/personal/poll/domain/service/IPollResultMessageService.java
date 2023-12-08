package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollReportDTO;

public interface IPollResultMessageService {

    void send(PollReportDTO pollReportDTO);
}
