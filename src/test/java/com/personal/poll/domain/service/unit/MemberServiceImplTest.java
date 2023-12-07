package com.personal.poll.domain.service.unit;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.exception.MemberCpfNotUniqueException;
import com.personal.poll.domain.fixture.member.dto.MemberCreateDTOFixture;
import com.personal.poll.domain.fixture.poll.models.MemberEntityFixture;
import com.personal.poll.domain.models.MemberEntity;
import com.personal.poll.domain.repository.IMemberRepository;
import com.personal.poll.domain.service.IVoteCountService;
import com.personal.poll.domain.service.implementation.MemberServiceImpl;
import com.personal.poll.util.AssertUtils;
import com.personal.poll.util.RandomUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private IMemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl service;

    private static MemberCreateDTO memberDTO;

    @Test
    void shouldCreateNewMemberSuccessfully() {
        memberDTO = MemberCreateDTOFixture.random();

        when(memberRepository.save(any())).then(this::setIdAndReturnsFirstArg);
        var memberViewDTO = service.create(memberDTO);

        assertEquals(memberDTO.getName(), memberViewDTO.getName());
        assertNotNull(memberViewDTO.getId());
    }

    @Test
    void shouldThrowMemberCpfNotUniqueExceptionWhenConstraintViolationExceptionIsThrown() {
        memberDTO = MemberCreateDTOFixture.random();
        when(memberRepository.save(any())).thenThrow(ConstraintViolationException.class);
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