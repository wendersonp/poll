package com.personal.poll.domain.service;

public interface IValidateMemberDocumentService {

    boolean shouldMemberVote(String cpfNumber);
}
