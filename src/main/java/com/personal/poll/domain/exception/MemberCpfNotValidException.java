package com.personal.poll.domain.exception;

import com.personal.poll.domain.util.ExceptionMessages;

public class MemberCpfNotValidException extends RuntimeException{

    public MemberCpfNotValidException() {
        super(ExceptionMessages.MEMBER_CPF_IS_NOT_VALID);
    }

}
