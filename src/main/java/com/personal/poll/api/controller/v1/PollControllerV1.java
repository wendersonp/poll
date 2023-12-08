package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollStartDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.service.IPollQueryService;
import com.personal.poll.domain.service.IPollService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/poll", produces = "application/vnd.example.api.v1+json")
@RequiredArgsConstructor
public class PollControllerV1 {

    private final IPollService service;

    private final IPollQueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PollViewDTO create(@RequestBody PollCreateDTO pollCreateDTO){
        return service.create(pollCreateDTO);
    }

    @PutMapping("/start/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PollStartDTO start(@PathVariable @Positive Long id,
                              @RequestParam @Positive Long duration) {
        return service.start(id, duration);
    }

    @GetMapping()
    public List<PollViewDTO> findAll() {
        return queryService.findAll();
    }

    @GetMapping("/{id}")
    public PollViewDTO find(@PathVariable Long id) {
        return queryService.findOne(id);
    }
}
