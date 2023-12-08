package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.member.MemberViewDTO;

import java.util.List;

public interface IMemberQueryService {

    MemberViewDTO findOne(Long id);
    List<MemberViewDTO> findAll();
}
