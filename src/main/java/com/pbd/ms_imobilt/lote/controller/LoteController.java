package com.pbd.ms_imobilt.lote.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.pbd.ms_imobilt.lote.dto.ObservationReqDto;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbd.ms_imobilt.client.service.ClientService;
import com.pbd.ms_imobilt.exception.DuplicateLoteClientException;
import com.pbd.ms_imobilt.lote.dto.InputReqLoteClientDto;
import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.lote.model.Type;
import com.pbd.ms_imobilt.lote.service.LoteClientService;
import com.pbd.ms_imobilt.lote.service.LoteService;
import com.pbd.ms_imobilt.responsedefault.RespIdDefaultDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/lote")
public class LoteController {

    @Autowired
    private  LoteService loteService;

    @Autowired
    private  ClientService clientService;

    @Autowired
    private  LoteClientService loteClientService;

    @GetMapping()
    public ResponseEntity<Map<String, List<LoteRespDto>>> getAllLotes(@RequestHeader("token") String tokenHearder){
        TokenHearder.token = tokenHearder;
        return ResponseEntity.ok(loteService.getAllLotesService());
    }

    @PostMapping("{id_lote}/reserve")
    public ResponseEntity<RespIdDefaultDto> postLoteReserve(
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @PathVariable(value = "id_lote") Integer id_lote,
            @RequestBody @Valid InputReqLoteClientDto clientID
            ){
        TokenHearder.token = tokenHeader;

        var client = clientService.findByIdService(clientID.client_id()).get();

        var lote = loteService.findByIDService(id_lote).get();

        if (!loteClientService.isLoteClientExists(
                client, lote, Type.RESERVE)){
            return loteClientService.saveService(
                    client, lote, Type.RESERVE
            );
        }

        throw new DuplicateLoteClientException("O cliente já tem reserva para esse lote criado em %s"
                .formatted(LocalDateTime.now()));
    }

    @PostMapping("{id_lote}/sale")
    public ResponseEntity<RespIdDefaultDto> postLoteSale(
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @PathVariable(value = "id_lote") Integer id_lote,
            @RequestBody @Valid InputReqLoteClientDto clientID
            ){
        TokenHearder.token = tokenHeader;

        var client = clientService.findByIdService(clientID.client_id()).get();

        var lote = loteService.findByIDService(id_lote).get();

        if (!loteClientService.isLoteClientExists(
                client, lote, Type.SALE)){

            return loteClientService.saveService(
                    client, lote, Type.SALE
            );

        }

        throw new DuplicateLoteClientException("O cliente já tem uma venda para esse lote criado em %s"
                .formatted(LocalDateTime.now()));
    }

    @PutMapping("{id_lote}/cancel")
    public void putLoteClientCancel(
            @PathVariable Integer id_lote,
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @RequestBody ObservationReqDto observationReqDto
    ) {

//        LoteClient loteClient = loteClientService.findByIdService(id_loi);
    }
}
