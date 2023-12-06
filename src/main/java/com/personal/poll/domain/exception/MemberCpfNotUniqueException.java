package com.personal.poll.domain.exception;

public class MemberCpfNotUniqueException extends RuntimeException{

    public MemberCpfNotUniqueException() {
        super();
    }

    public MemberCpfNotUniqueException(String message) {
        super(message);
    }

    public MemberCpfNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberCpfNotUniqueException(Throwable cause) {
        super(cause);
    }
}
