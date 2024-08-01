package com.pbd.ms_imobilt.client.controller;

import com.pbd.ms_imobilt.client.dto.ClientReqDto;
import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.client.service.ClientService;
import com.pbd.ms_imobilt.exception.ClientNotFoundException;
import com.pbd.ms_imobilt.responsedefault.RespIdDefaultDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create Client", description = "Method that creates a client", tags = "Client")
    @PostMapping
    public ResponseEntity<RespIdDefaultDto> postCreateClient(
            @RequestHeader(name = "token") String tokenHeader,
            @RequestBody @Valid ClientReqDto clientReqDto
            ){
        TokenHearder.token = tokenHeader;

        Client client = new Client();

        BeanUtils.copyProperties(clientReqDto, client);

        client = clientService.save(client);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespIdDefaultDto(client.getId()));

    }

    @Operation(summary = "Edit Client", description = "Method to edit a client", tags = "Client")
    @PutMapping("{id}")
    public ResponseEntity<RespIdDefaultDto> putEditClient(@PathVariable(value = "id") Integer id,
                                                @RequestHeader(name = "token") String tokenHeader,
                                                @RequestBody ClientReqDto clientReqDto){

        TokenHearder.token = tokenHeader;

        Optional<Client> oldClient = Optional.ofNullable(clientService.findById(id)
                .orElseThrow(ClientNotFoundException::new));

        BeanUtils.copyProperties(clientReqDto, oldClient.get());

        Client editedClient = clientService.save(
                oldClient.get()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespIdDefaultDto(
                       editedClient.getId()
                ));

    }


}
