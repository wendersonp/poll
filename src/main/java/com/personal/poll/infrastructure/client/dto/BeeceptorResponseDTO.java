package com.personal.poll.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.poll.infrastructure.client.enumeration.UserVoteStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeeceptorResponseDTO {

    @JsonProperty("status")
    private UserVoteStatusEnum voteAllowedStatus;
}
