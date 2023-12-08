package com.personal.poll.infrastructure.component;

import com.personal.poll.domain.dto.poll.PollResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class PollResultMessageChannel {

    private final Queue<PollResultDTO> pollResultQueue = new ConcurrentLinkedQueue<>();

    public void sendResultToChannel(PollResultDTO pollResultDTO) {
        pollResultQueue.offer(pollResultDTO);
    }
    @Bean
    public Supplier<PollResultDTO> voteReportSupplier() {
        return pollResultQueue::poll;
    }
}
