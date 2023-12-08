package com.personal.poll.infrastructure.messaging.component;

import com.personal.poll.domain.dto.poll.PollReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class PollResultMessageChannel {

    private final Queue<PollReportDTO> pollResultQueue = new ConcurrentLinkedQueue<>();

    public void sendResultToChannel(PollReportDTO pollReportDTO) {
        pollResultQueue.offer(pollReportDTO);
    }
    @Bean
    public Supplier<PollReportDTO> voteReportSupplier() {
        return pollResultQueue::poll;
    }
}
