package com.personal.poll.domain.models;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.enums.VoteValueEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade representativa da pauta
 */
@Entity
@Table(name = "tb_poll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollEntity {

    /**
     * Id da pauta
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Assunto da pauta
     */
    @Column(length = 60)
    private String subject;

    /**
     * Data do início da votação
     */
    private LocalDateTime startTime;

    /**
     * Data do fim da votação
     */
    private LocalDateTime endTime;

    /**
     * Total de votos {@link VoteValueEnum#YES} registrados na pauta
     */
    private long totalPositiveVotes;

    /**
     * Total de votos {@link VoteValueEnum#NO} registrados na pauta
     */
    private long totalNegativeVotes;

    /**
     * Status da votação na pauta, dado pelos valores em {@link PollStatusEnum}
     */
    private PollStatusEnum status;

    /**
     * Voto vencedor da pauta com votação encerrada, dado em {@link VoteValueEnum}
     */
    private VoteValueEnum winningVote;

    /**
     * Votos cadastrados na pauta, recuperação LAZY por padrão
     */
    @OneToMany(mappedBy = "agenda")
    private List<VoteEntity> votes;

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
