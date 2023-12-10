package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.models.VoteEntity;

/**
 * Serviço para cadastro e gerenciamendo das regras de negócio de votos
 */
public interface IVoteService {

    /**
     * Registra o voto de um associado válido em uma pauta, um associado pode votar em uma pauta
     * apenas uma vez
     * @param voteRegistry Dados com identificador da pauta, associado, e o voto
     * @return Comprovante de votação na pauta em questão
     */
    VoteConfirmationDTO registerVote(VoteRegistryDTO voteRegistry);

    /**
     * Busca o voto de um associado em uma pauta
     * @param id Id do voto na pauta
     * @return Voto do associado
     */
    VoteEntity find(Long id);
}
