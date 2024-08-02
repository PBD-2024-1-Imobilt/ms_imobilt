package com.pbd.ms_imobilt.lote.controller;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.client.service.ClientService;
import com.pbd.ms_imobilt.lote.dto.InputReqLoteClientDto;
import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.lote.dto.ObservationReqDto;
import com.pbd.ms_imobilt.lote.exception.LoteCiientCancelException;
import com.pbd.ms_imobilt.lote.exception.ObservationFieldException;
import com.pbd.ms_imobilt.lote.exception.SaleException;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import com.pbd.ms_imobilt.lote.model.Type;
import com.pbd.ms_imobilt.lote.service.LoteClientService;
import com.pbd.ms_imobilt.lote.service.LoteService;
import com.pbd.ms_imobilt.responsedefault.RespIdDefaultDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/lote")
public class LoteController {

    @Autowired
    private  LoteService loteService;

    @Autowired
    private  ClientService clientService;

    @Autowired
    private  LoteClientService loteClientService;

    @Operation(summary = "List Lote", description = "Method to list lotes", tags = "Lote")
    @GetMapping()
    public ResponseEntity<Map<String, List<LoteRespDto>>> getAllLotes(@RequestHeader("token") String tokenHearder){
        TokenHearder.token = tokenHearder;
        return ResponseEntity.ok(loteService.getAllLotes());
    }

    @Operation(summary = "Lote Reserve", description = "Method to reserve a lote", tags = "Lote")
    @PostMapping("{id_lote}/reserve")
    public ResponseEntity<RespIdDefaultDto> postLoteReserve(
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @PathVariable(value = "id_lote") Integer id_lote,
            @RequestBody @Valid InputReqLoteClientDto clientID
    ){
        TokenHearder.token = tokenHeader;

        var client = clientService.findById(clientID.client_id()).get();

        var lote = loteService.findByID(id_lote);

        return loteClientService.save(client, lote, Type.RESERVE);
    }

    @Operation(summary = "Lote Sale", description = "Method for selling a lote", tags = "Lote")
    @PostMapping("{id_lote}/sale")
    public ResponseEntity<RespIdDefaultDto> postLoteSale(
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @PathVariable(value = "id_lote") Integer id_lote,
            @RequestBody @Valid InputReqLoteClientDto clientID
    ){
        TokenHearder.token = tokenHeader;

        Client client = clientService.findById(clientID.client_id()).get();

        Lote lote = loteService.findByID(id_lote);

        if (loteClientService.isLoteReservedByAnotherClient(client, lote))
            throw new SaleException("Sale of a lot reserved by another client is not allowed",
                    HttpStatus.BAD_REQUEST);

        return loteClientService.save(client, lote, Type.SALE);
    }

    @Operation(summary = "Lote Cancel", description = "Method to cancel a lote", tags = "Lote")
    @PutMapping("{id_lote}/cancel")
    public ResponseEntity<RespIdDefaultDto> putLoteClientCancel(
            @PathVariable Integer id_lote,
            @RequestHeader(TokenHearder.TOKENNAME) String tokenHeader,
            @RequestBody ObservationReqDto observationReqDto
    ) {
        TokenHearder.token = tokenHeader;

        Lote lote = loteService.findByID(id_lote);

        LoteClient loteClientOld = loteClientService.findByLote(lote)
                .stream().filter(
                        l -> l.getType() == Type.SALE || l.getType() == Type.RESERVE
                )
                .findFirst().orElseThrow(
                        () ->  new LoteCiientCancelException("This loteClient has already been cancelled",
                                HttpStatus.BAD_REQUEST)
                );

        if (loteClientOld.getType() == Type.SALE && observationReqDto.observation().isEmpty())
                throw new ObservationFieldException("When the previous LotClient type is SALE, this field is mandatory!",
                        HttpStatus.BAD_REQUEST);
        return loteClientService.loteClientCancel(loteClientOld, observationReqDto);
    }
}
