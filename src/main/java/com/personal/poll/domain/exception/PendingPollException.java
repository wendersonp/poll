package com.personal.poll.domain.exception;

import com.personal.poll.domain.util.ExceptionMessages;

public class PendingPollException extends RuntimeException{
    public PendingPollException() {
        super(ExceptionMessages.PENDING_POLL);
    }
}
