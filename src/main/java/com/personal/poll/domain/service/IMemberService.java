package com.personal.poll.domain.service;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;

public interface IMemberService {

    MemberViewDTO create(MemberCreateDTO member);
}
