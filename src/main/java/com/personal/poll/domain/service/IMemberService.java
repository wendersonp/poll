package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.models.MemberEntity;

import java.util.List;

/**
 * Interface para cadastro e manipulação dos associados do sistema,
 * Contém regras de negócio relacionadas ao associado
 */
public interface IMemberService {

    /**
     * Cadastra um associado no sistema de votação
     * @param member Dados do associado
     * @return Visualização do associado cadastrado
     */
    MemberViewDTO create(MemberCreateDTO member);

    /**
     * Busca um associado no sistema, através do Id, pode ser usada para manipular seus dados
     * @param id Id do associado
     * @return Associado cadastrado
     */
    MemberEntity find(Long id);

    /**
     * Busca a lista com todos os associados cadastrados no sistema
     * @return Todos os associados cadastrados no sistema
     */
    List<MemberEntity> findAll();
}
