package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.models.MemberEntity;

public interface IMemberService {

    MemberViewDTO create(MemberCreateDTO member);

    MemberEntity find(Long id);
}
