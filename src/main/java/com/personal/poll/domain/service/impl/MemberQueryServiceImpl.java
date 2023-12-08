package com.personal.poll.domain.service.impl;

import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.service.IMemberQueryService;
import com.personal.poll.domain.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements IMemberQueryService {

    private final IMemberService memberService;


    @Override
    public MemberViewDTO findOne(Long id) {
        return new MemberViewDTO(memberService.find(id));
    }

    @Override
    public List<MemberViewDTO> findAll() {
        return memberService
                .findAll()
                .stream()
                .map(MemberViewDTO::new)
                .toList();
    }
}
