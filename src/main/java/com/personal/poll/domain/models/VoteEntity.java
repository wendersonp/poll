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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private VoteValueEnum vote;

    @ManyToOne
    @JoinColumn(name = "fk_agenda", nullable = false)
    private PollEntity agenda;

    @ManyToOne
    @JoinColumn(name = "fk_voter", nullable = false)
    private MemberEntity voter;
}
