package com.personal.poll.infrastructure.client.external;

import com.personal.poll.infrastructure.client.dto.BeeceptorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "beeceptor-client", url = "${beeceptor.client.url}")
public interface BeeceptorDocumentApiClient {

    @GetMapping("/users/{cpf}")
    BeeceptorResponseDTO validateVotingStatus(@RequestParam(name = "cpf") String documentNumber);
}
