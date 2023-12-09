package com.personal.poll.domain.service.impl;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.exception.MemberCpfNotAllowedException;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.repository.IMemberRepository;
import com.personal.poll.domain.service.IMemberService;
import com.personal.poll.domain.service.IValidateMemberDocumentService;
import com.personal.poll.util.ExceptionMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService {

    private final IMemberRepository memberRepository;

    private final IValidateMemberDocumentService validateMemberDocumentService;

    @Override
    public MemberViewDTO create(MemberCreateDTO member) {
        validateMember(member);
        MemberEntity memberEntity = member.toEntity();

        try {
            memberEntity = memberRepository.save(memberEntity);
            return new MemberViewDTO(memberEntity);
        } catch (DataIntegrityViolationException exception) {
            log.warn("Membro de documento {} já existe em sistema", memberEntity.getCpfNumber());
            throw new MemberCpfNotUniqueException();
        }
    }

    @Override
    public MemberEntity find(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessages.MEMBER_NOT_FOUND));
    }

    @Override
    public List<MemberEntity> findAll() {
        return memberRepository.findAll();
    }

    private void validateMember(MemberCreateDTO member) {
        if (!validateMemberDocumentService.shouldMemberVote(member.getCpfNumber())) {
            throw new MemberCpfNotAllowedException();
        }
    }
}
