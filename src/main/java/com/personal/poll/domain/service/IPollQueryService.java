package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.poll.PollViewDTO;

import java.util.List;

public interface IPollQueryService {

    PollViewDTO findOne(Long id);

    List<PollViewDTO> findAll();
}
