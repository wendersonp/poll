package com.personal.poll.domain.models;

import com.personal.poll.domain.enums.VoteValueEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entidade representativa de um voto
 */
@Entity
@Table(name = "tb_vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_agenda", "fk_voter"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteEntity {

    /**
     * Id do voto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valor do voto, pode ser {@link VoteValueEnum#YES} ou
     * {@link VoteValueEnum#NO}
     */
    @Column(nullable = false)
    private VoteValueEnum vote;

    /**
     * Pauta referente ao voto dado
     */
    @ManyToOne
    @JoinColumn(name = "fk_agenda", nullable = false)
    private PollEntity agenda;

    /**
     * Associado votante
     */
    @ManyToOne
    @JoinColumn(name = "fk_voter", nullable = false)
    private MemberEntity voter;
}
