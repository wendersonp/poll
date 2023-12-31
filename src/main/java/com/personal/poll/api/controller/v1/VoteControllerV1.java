package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.vote.VoteConfirmationDTO;
import com.personal.poll.domain.dto.vote.VoteRegistryDTO;
import com.personal.poll.domain.service.IVoteQueryService;
import com.personal.poll.domain.service.IVoteService;
import com.personal.poll.api.controller.v1.tags.ControllerTags;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = ControllerTags.VOTE_CONTROLLER_V1_TAG)
public class VoteControllerV1 {

    private final IVoteService service;

    private final IVoteQueryService queryService;

    @Operation(summary = "Registra um voto de um associado em uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto confirmado"),
            @ApiResponse(responseCode = "400", description = "Pauta não está em votação"),
            @ApiResponse(responseCode = "404", description = "Associado ou pauta não existem"),
            @ApiResponse(responseCode = "409", description = "Voto em questão já foi registrado")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteConfirmationDTO register(@RequestBody @Valid VoteRegistryDTO vote){
        return service.registerVote(vote);
    }

    @Operation(summary = "Procura pela confirmação de um voto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmação encontrada"),
            @ApiResponse(responseCode = "404", description = "Voto não existe no sistema"),
    })
    @GetMapping("/{id}")
    public VoteConfirmationDTO find(@PathVariable Long id) {
        return queryService.find(id);
    }
}
