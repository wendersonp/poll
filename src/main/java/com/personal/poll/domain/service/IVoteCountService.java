package com.personal.poll.domain.service;

/**
 * Interface com as regras de negócio para apuração dos votos e fornecimento do resultado
 */
public interface IVoteCountService {
    /**
     * Inicia a apuração dos votos dados em uma pauta, a apuração só inicia se a duração da
     * votação tiver expirada
     * @param pollId Identificador da pauta para apuração
     */
    void countVotesOfPoll(Long pollId);
}
