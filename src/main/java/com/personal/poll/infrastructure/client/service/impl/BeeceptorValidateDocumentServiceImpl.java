package com.personal.poll.infrastructure.client.service.impl;

import com.personal.poll.domain.exception.MemberCpfNotValidException;
import com.personal.poll.domain.service.IValidateMemberDocumentService;
import com.personal.poll.infrastructure.client.dto.BeeceptorResponseDTO;
import com.personal.poll.infrastructure.client.enumeration.UserVoteStatusEnum;
import com.personal.poll.infrastructure.client.external.BeeceptorDocumentApiClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeeceptorValidateDocumentServiceImpl implements IValidateMemberDocumentService {

    private final BeeceptorDocumentApiClient documentApiClient;
    @Override
    public boolean shouldMemberVote(String cpfNumber) {
        try {
            BeeceptorResponseDTO response = documentApiClient.validateVotingStatus(cpfNumber);
            return UserVoteStatusEnum.isAbleToVote(response.getVoteAllowedStatus());
        } catch (FeignException.NotFound exception) {
            log.warn("Cpf {} of member is not valid", cpfNumber);
            throw new MemberCpfNotValidException();
        }
    }
}
