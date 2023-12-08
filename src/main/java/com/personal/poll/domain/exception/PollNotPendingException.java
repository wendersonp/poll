package com.personal.poll.domain.exception;

import com.personal.poll.util.ExceptionMessages;

public class PollNotPendingException extends RuntimeException{
    public PollNotPendingException() {
        super(ExceptionMessages.POLL_NOT_PENDING);
    }
}
