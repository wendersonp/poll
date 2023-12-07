package com.personal.poll.domain.models;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_poll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 60)
    private String subject;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long totalPositiveVotes;

    private long totalNegativeVotes;

    private PollStatusEnum status;

    private VoteValueEnum winningVote;

    public void openVoting(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = PollStatusEnum.OPEN;
    }

    public void calculateVote(VoteValueEnum vote) {
        if (VoteValueEnum.YES.equals(vote)) {
            totalPositiveVotes = totalPositiveVotes + 1;
        } else {
            totalNegativeVotes = totalNegativeVotes + 1;
        }
    }
}
