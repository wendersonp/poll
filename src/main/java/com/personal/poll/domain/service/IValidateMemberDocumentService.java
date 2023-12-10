package com.personal.poll.domain.service;

/**
 * Interface para validação dos documentos do associado
 */
public interface IValidateMemberDocumentService {

    /**
     * Verifica se um associado pode votar nas pautas validando seu CPF
     * @param cpfNumber Número de CPF do associado, em formato de 11 dígitos
     * @return Validação se o associado pode votar ou não
     */
    boolean shouldMemberVote(String cpfNumber);
}
