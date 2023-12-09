package com.personal.poll.util;

public class ExceptionMessages {
    public static final String MEMBER_NOT_FOUND = "Member was not found";

    public static final String MEMBER_CPF_IS_NOT_VALID = "The cpf number inserted is invalid";

    public static final String MEMBER_CPF_IS_NOT_ALLOWED = "The member with this cpf is not allowed to vote";

    public static final String MEMBER_CPF_NOT_UNIQUE = "Member document number is not unique";

    public static final String POLL_NOT_FOUND = "Poll was not found";


    public static final String PENDING_POLL = "Voting for this poll did not start yet";

    public static final String POLL_NOT_PENDING = "This poll is not pending to be voted";

    public static final String CLOSED_POLL = "Voting for this poll is already closed";

    public static final String VOTE_ALREADY_REGISTERED = "Vote of this associate for current agenda was already registered";


    private ExceptionMessages(){
    }
}
