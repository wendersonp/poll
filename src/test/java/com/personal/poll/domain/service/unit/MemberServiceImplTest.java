package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.exception.MemberCpfNotAllowedException;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.fixture.member.dto.MemberCreateDTOFixture;
import com.personal.poll.domain.fixture.member.models.MemberEntityFixture;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.repository.IMemberRepository;
import com.personal.poll.domain.service.IValidateMemberDocumentService;
import com.personal.poll.domain.service.impl.MemberServiceImpl;
import com.personal.poll.util.AssertUtils;
import com.personal.poll.util.RandomUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private IMemberRepository memberRepository;

    @Mock
    private IValidateMemberDocumentService validateDocumentService;

    @InjectMocks
    private MemberServiceImpl service;

    private static MemberCreateDTO memberDTO;

    @Test
    void shouldCreateNewMemberSuccessfully() {
        memberDTO = MemberCreateDTOFixture.random();


        when(validateDocumentService.shouldMemberVote(any())).thenReturn(true);
        when(memberRepository.save(any())).then(this::setIdAndReturnsFirstArg);
        var memberViewDTO = service.create(memberDTO);

        assertEquals(memberDTO.getName(), memberViewDTO.getName());
        assertNotNull(memberViewDTO.getId());
    }

    @Test
    void shouldThrowMemberCpfNotAllowedExceptionWhenValidating() {
        memberDTO = MemberCreateDTOFixture.random();
        when(validateDocumentService.shouldMemberVote(any())).thenReturn(false);
        assertThrows(MemberCpfNotAllowedException.class, () -> service.create(memberDTO));
    }

    @Test
    void shouldThrowMemberCpfNotUniqueExceptionWhenConstraintViolationExceptionIsThrown() {
        memberDTO = MemberCreateDTOFixture.random();
        when(validateDocumentService.shouldMemberVote(any())).thenReturn(true);
        when(memberRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(MemberCpfNotUniqueException.class, () -> service.create(memberDTO));
    }

    @Test
    void shouldFindMemberSuccessfully() {
        MemberEntity member = MemberEntityFixture.random();
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        var foundMember = service.find(member.getId());
        AssertUtils.assertAllFields(member, foundMember);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenMemberNotFound() {
        Long id = RandomUtils.random.nextLong();
        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.find(id));
    }

    private MemberEntity setIdAndReturnsFirstArg(InvocationOnMock invocationOnMock) {
        MemberEntity object = invocationOnMock.getArgument(0);
        assertNotNull(object.getCpfNumber());
        object.setId(RandomUtils.random.nextLong());
        return object;
    }

}