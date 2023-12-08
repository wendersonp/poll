package com.personal.poll.domain.service.implementation;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.repository.IMemberRepository;
import com.personal.poll.domain.service.IMemberService;
import com.personal.poll.util.ExceptionMessages;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService {

    private final IMemberRepository memberRepository;

    @Override
    public MemberViewDTO create(MemberCreateDTO member) {
        //TODO: Criar Validação com API externa para CPF do membro
        MemberEntity memberEntity = member.toEntity();
        try {
            memberEntity = memberRepository.save(memberEntity);
            return new MemberViewDTO(memberEntity);
        } catch (ConstraintViolationException exception) {
            log.warn("Membro de documento {} já existe em sistema", memberEntity.getCpfNumber());
            throw new MemberCpfNotUniqueException();
        }
    }

    @Override
    public MemberEntity find(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessages.MEMBER_NOT_FOUND));
    }
}
