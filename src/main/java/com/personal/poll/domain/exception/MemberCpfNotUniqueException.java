package com.personal.poll.domain.exception;

import com.personal.poll.domain.util.ExceptionMessages;

public class MemberCpfNotUniqueException extends RuntimeException{

    public MemberCpfNotUniqueException() {
        super(ExceptionMessages.MEMBER_CPF_NOT_UNIQUE);
    }

}
