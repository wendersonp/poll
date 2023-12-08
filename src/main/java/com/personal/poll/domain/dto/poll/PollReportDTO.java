package com.personal.poll.domain.dto.poll;

import com.personal.poll.domain.dto.vote.VoteReportDTO;
import com.personal.poll.domain.enums.VoteValueEnum;
import com.personal.poll.domain.models.PollEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollReportDTO {

    private String subject;

    private VoteValueEnum winner;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private long totalVotes;

    private long positiveVotes;

    private long negativeVotes;

    private List<VoteReportDTO> voteReport;

    public PollReportDTO(PollEntity poll) {
        this.subject = poll.getSubject();
        this.winner = poll.getWinningVote();
        this.startTime = poll.getStartTime();
        this.totalVotes = poll.getTotalNegativeVotes() + poll.getTotalPositiveVotes();
        this.positiveVotes = poll.getTotalPositiveVotes();
        this.negativeVotes = poll.getTotalNegativeVotes();
        this.voteReport = poll.getVotes().stream().map(VoteReportDTO::new).toList();
    }
}
