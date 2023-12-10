package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollStartDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.models.PollEntity;

import java.util.List;

/**
 * Serviço para manipulação de pautas, contém as regras de negócio relacionadas as pautas
 */
public interface IPollService {

    /**
     * Cria uma pauta a ser votada pelos associados, uma pauta criada fica com o status PENDING
     * até sua votação ser iniciada
     * @param pollCreate Objeto de criação da pauta, que contém o assunto a ser votado
     * @return Visualização da pauta
     */
    PollViewDTO create(PollCreateDTO pollCreate);

    /**
     * Inicia a votação de uma pauta, colocando seu status em OPEN, a votação é encerrada e fica com status
     * CLOSED após sua duração, especificada em segundos, se encerrar. Apenas uma pauta com status PENDING
     * pode ser iniciada
     * @param id Identificador da pauta a ser votada
     * @param durationInSeconds Tempo de duração da votação, em Segundos
     * @return Visualização dos dados relacionados a votação
     */
    PollStartDTO start(Long id, Long durationInSeconds);

    /**
     * Busca uma pauta cadastrada através de seu Identificador
     * @param id Id da pauta
     * @return Pauta buscada
     */
    PollEntity find(Long id);

    /**
     * Busca todas as pautas cadastradas no sistema
     * @return Lista de pautas cadastradas
     */
    List<PollEntity> findAll();
}
