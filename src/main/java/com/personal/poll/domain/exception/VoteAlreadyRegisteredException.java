package com.personal.poll.domain.exception;

import com.personal.poll.domain.util.ExceptionMessages;

public class VoteAlreadyRegisteredException extends RuntimeException{
    public VoteAlreadyRegisteredException() {
        super(ExceptionMessages.VOTE_ALREADY_REGISTERED);
    }
}
