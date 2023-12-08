package com.personal.poll.domain.exception;

import com.personal.poll.util.ExceptionMessages;

public class MemberCpfNotAllowedException extends RuntimeException{
    public MemberCpfNotAllowedException() {
        super(ExceptionMessages.MEMBER_CPF_IS_NOT_ALLOWED);
    }
}
