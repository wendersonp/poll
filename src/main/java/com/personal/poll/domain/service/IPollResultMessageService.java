package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollReportDTO;

/**
 * Interface para envio de resultado da apuração de votos de uma pauta por mensagem
 */
public interface IPollResultMessageService {

    /**
     * Envia o resultado de uma pauta para messageria ou outro serviço que implemente esta interface
     * @param pollReportDTO Objeto com os dados da apuração e resultado
     */
    void send(PollReportDTO pollReportDTO);
}
