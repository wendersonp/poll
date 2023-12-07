package com.personal.poll.domain.exception;

import com.personal.poll.util.ExceptionMessages;

public class VoteAlreadyRegisteredException extends RuntimeException{
    public VoteAlreadyRegisteredException() {
        super(ExceptionMessages.VOTE_ALREADY_REGISTERED);
    }
}
