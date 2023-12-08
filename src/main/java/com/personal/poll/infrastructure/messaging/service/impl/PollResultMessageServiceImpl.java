package com.personal.poll.infrastructure.messaging.service.impl;

import com.personal.poll.domain.dto.poll.PollReportDTO;
import com.personal.poll.domain.service.IPollResultMessageService;
import com.personal.poll.infrastructure.messaging.component.PollResultMessageChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PollResultMessageServiceImpl implements IPollResultMessageService {

    private final PollResultMessageChannel channel;

    @Override
    public void send(PollReportDTO pollReportDTO) {
        channel.sendResultToChannel(pollReportDTO);
    }

}
