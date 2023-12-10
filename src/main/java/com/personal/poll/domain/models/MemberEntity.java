package com.personal.poll.domain.models;


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

/**
 * Entidade do associado
 */
@Entity
@Table(name = "tb_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    /**
     * Id do associado
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do associado, máximo 40 caractéres
     */
    @Column(columnDefinition = "VARCHAR(40)")
    private String name;

    /**
     * CPF do associado, no formato de 11 dígitos
     */
    @Column(length = 11, unique = true)
    private String cpfNumber;
}
