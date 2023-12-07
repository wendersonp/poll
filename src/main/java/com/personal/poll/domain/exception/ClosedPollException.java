package com.personal.poll.domain.exception;

import com.personal.poll.util.ExceptionMessages;

public class ClosedPollException extends RuntimeException{
    public ClosedPollException() {
        super(ExceptionMessages.CLOSED_POLL);
    }

}
