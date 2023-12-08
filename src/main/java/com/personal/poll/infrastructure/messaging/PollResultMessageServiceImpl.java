package com.personal.poll.infrastructure.messaging;

import com.personal.poll.domain.dto.poll.PollResultDTO;
import com.personal.poll.domain.service.IPollResultMessageService;
import com.personal.poll.infrastructure.component.PollResultMessageChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PollResultMessageServiceImpl implements IPollResultMessageService {

    private final PollResultMessageChannel channel;

    @Override
    public void send(PollResultDTO pollResultDTO) {
        channel.sendResultToChannel(pollResultDTO);
    }

}
