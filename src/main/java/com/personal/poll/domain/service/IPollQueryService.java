package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollViewDTO;

import java.util.List;

/**
 * Interface para busca e visualização de dados de pautas, votadas ou não votadas
 */
public interface IPollQueryService {

    /**
     * Busca por uma pauta por seu identificador
     * @param id Id da pauta a ser buscada
     * @return Objeto para visualização dos dados da pauta
     */
    PollViewDTO findOne(Long id);

    /**
     * Busca todas as pautas cadastradas no sistema
     * @return Lista com todas as pautas cadastradas
     */
    List<PollViewDTO> findAll();
}
