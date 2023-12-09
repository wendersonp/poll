package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.poll.PollCreateDTO;
import com.personal.poll.domain.dto.poll.PollStartDTO;
import com.personal.poll.domain.dto.poll.PollViewDTO;
import com.personal.poll.domain.service.IPollQueryService;
import com.personal.poll.domain.service.IPollService;
import com.personal.poll.util.ControllerTags;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = ControllerTags.POLL_CONTROLLER_V1_TAG)
public class PollControllerV1 {

    private final IPollService service;

    private final IPollQueryService queryService;

    @Operation(summary = "Registra uma pauta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Um ou varios campos são invalidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PollViewDTO create(@RequestBody @Valid PollCreateDTO pollCreateDTO){
        return service.create(pollCreateDTO);
    }

    @Operation(summary = "Inicia a votação de uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Um ou varios campos são invalidos")
    })
    @PutMapping("/start/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PollStartDTO start(@PathVariable @Positive Long id,
                              @RequestParam @Positive Long duration) {
        return service.start(id, duration);
    }

    @Operation(summary = "Busca a lista de pautas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornece lista de pautas")
    })
    @GetMapping()
    public List<PollViewDTO> findAll() {
        return queryService.findAll();
    }

    @Operation(summary = "Busca a pauta cadastrada por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornece pauta encontrada"),
            @ApiResponse(responseCode = "404", description = "Pauta em questão nao foi encontrada")
    })
    @GetMapping("/{id}")
    public PollViewDTO find(@PathVariable Long id) {
        return queryService.findOne(id);
    }
}
