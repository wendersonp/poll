package com.personal.poll.domain.service.impl;


import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.service.IPollQueryService;
import com.personal.poll.domain.service.IPollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollQueryServiceImpl implements IPollQueryService {

    private final IPollService pollService;

    @Override
    public PollViewDTO findOne(Long id) {
        return new PollViewDTO(pollService.find(id));
    }

    @Override
    public List<PollViewDTO> findAll() {
        return pollService
                .findAll()
                .stream()
                .map(PollViewDTO::new)
                .toList();
    }
}
