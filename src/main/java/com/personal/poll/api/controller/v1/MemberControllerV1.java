package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.service.IMemberQueryService;
import com.personal.poll.domain.service.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/member", produces = "application/vnd.example.api.v1+json")
@RequiredArgsConstructor
public class MemberControllerV1 {

    private final IMemberService service;

    private final IMemberQueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberViewDTO create(@RequestBody MemberCreateDTO memberCreateDTO) {
        return service.create(memberCreateDTO);
    }

    @GetMapping()
    public List<MemberViewDTO> findAll() {
        return queryService.findAll();
    }

    @GetMapping("/{id}")
    public MemberViewDTO find(@PathVariable Long id) {
        return queryService.findOne(id);
    }
}
