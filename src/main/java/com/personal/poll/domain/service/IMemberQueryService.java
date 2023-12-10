package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.member.MemberViewDTO;

import java.util.List;

/**
 * Interface para visualização de dados de associados
 */
public interface IMemberQueryService {

    /**
     * Busca um associado cadastrado no sistema para visualização
     * @param id Id do associado no banco
     * @return Associado cadastrado, caso encontrado.
     */
    MemberViewDTO findOne(Long id);

    /**
     * Busca a lista de associados cadastrados, para visualização
     * @return Lista de associados cadastrados
     */
    List<MemberViewDTO> findAll();
}
