package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.service.IVoteQueryService;
import com.personal.poll.domain.service.IVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vote", produces = "application/vnd.example.api.v1+json")
@RequiredArgsConstructor
public class VoteControllerV1 {

    private final IVoteService service;

    private final IVoteQueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteConfirmationDTO register(@RequestBody VoteRegistryDTO vote){
        return service.registerVote(vote);
    }

    @GetMapping("/{id}")
    public VoteConfirmationDTO find(@PathVariable Long id) {
        return queryService.find(id);
    }
}
