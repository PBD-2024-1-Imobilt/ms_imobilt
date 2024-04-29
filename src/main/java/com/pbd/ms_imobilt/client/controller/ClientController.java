package com.pbd.ms_imobilt.client.controller;

import com.pbd.ms_imobilt.client.dto.ClientReqDto;
import com.pbd.ms_imobilt.client.dto.ClientRespDto;
import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.client.service.ClientService;
import com.pbd.ms_imobilt.exception.ClientNotFoundException;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping("{id}")
    public ResponseEntity<ClientRespDto> putEditClient(@PathVariable(value = "id") Integer id,
                                                @RequestHeader(name = "token") String tokenHeader,
                                                @RequestBody ClientReqDto clientReqDto){

        TokenHearder.setToken(tokenHeader);

        Optional<Client> oldClient = Optional.ofNullable(clientService.findByIdService(id)
                .orElseThrow(ClientNotFoundException::new));

        BeanUtils.copyProperties(clientReqDto, oldClient.get());

        Client editedClient = clientService.saveService(oldClient.get());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ClientRespDto(
                       editedClient.getId()
                ));

    }


}
