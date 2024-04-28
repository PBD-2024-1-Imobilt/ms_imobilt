package com.pbd.ms_imobilt.client.controller;

import com.pbd.ms_imobilt.client.dto.ClientReqDto;
import com.pbd.ms_imobilt.client.dto.ClientRespDto;
import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.client.service.ClientService;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientRespDto> postCreateClient(
            @RequestHeader(name = "token") String tokenHeader,
            @RequestBody @Valid ClientReqDto clientReqDto
            ){
        TokenHearder.setToken(tokenHeader);

        Client client = new Client();

        BeanUtils.copyProperties(clientReqDto, client);

        client = clientService.saveService(client);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ClientRespDto(client.getId()));

    }

}
