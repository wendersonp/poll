package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;

/**
 * Interface para busca do comprovante de voto pelo Id
 */
public interface IVoteQueryService {

    /**
     * Retorna o comprovante do voto através de seu identificador
     * @param id Identificador do voto
     * @return Visualização do comprovante do voto
     */
    VoteConfirmationDTO find(Long id);
}
