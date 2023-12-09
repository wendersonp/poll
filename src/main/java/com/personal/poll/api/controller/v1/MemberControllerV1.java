package com.personal.poll.api.controller.v1;

import com.personal.poll.domain.dto.member.MemberCreateDTO;
import com.personal.poll.domain.dto.member.MemberViewDTO;
import com.personal.poll.domain.service.IMemberQueryService;
import com.personal.poll.domain.service.IMemberService;
import com.personal.poll.util.ControllerTags;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = ControllerTags.MEMBER_CONTROLLER_V1_TAG)
public class MemberControllerV1 {


    private final IMemberService service;

    private final IMemberQueryService queryService;

    @Operation(summary = "Registra um associado no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Membro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Um ou varios campos são invalidos"),
            @ApiResponse(responseCode = "409", description = "Membro com mesmo cpf já existe na base de dados")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberViewDTO create(@RequestBody MemberCreateDTO memberCreateDTO) {
        return service.create(memberCreateDTO);
    }

    @Operation(summary = "Busca todos os associados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista dos associados")
    })
    @GetMapping()
    public List<MemberViewDTO> findAll() {
        return queryService.findAll();
    }

    @Operation(summary = "Busca associado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado encontrado"),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado")
    })
    @GetMapping("/{id}")
    public MemberViewDTO find(@PathVariable Long id) {
        return queryService.findOne(id);
    }
}
