package com.personal.poll.infrastructure.client.enumeration;

public enum UserVoteStatusEnum {
    ABLE_TO_VOTE,
    UNABLE_TO_VOTE;

    public static boolean isAbleToVote(UserVoteStatusEnum status) {
        return ABLE_TO_VOTE.equals(status);
    }
}
