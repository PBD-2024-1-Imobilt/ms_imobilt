package com.pbd.ms_imobilt.lote.service;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.dto.LoteClientReqDto;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import com.pbd.ms_imobilt.lote.model.Type;
import com.pbd.ms_imobilt.lote.repository.LoteClientRepository;
import com.pbd.ms_imobilt.responsedefault.RespIdDefaultDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoteClientService {

    private final LoteClientRepository loteClientRepository;

    private final TokenAuthenticationService authToken;

    @Transactional
    public ResponseEntity<RespIdDefaultDto> executeSaveService(Client client, Lote lote, Type type){

        LoteClient loteClient = new LoteClient();

        BeanUtils.copyProperties(new LoteClientReqDto(client, lote, type, LocalDateTime.now()), loteClient);

        loteClient = saveService(loteClient);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespIdDefaultDto(loteClient.getId()));
    }

    @Transactional
    public LoteClient saveService(LoteClient loteClient){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return loteClientRepository.save(loteClient);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    public boolean isLoteClientExists(Client client, Lote lote, Type type){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return loteClientRepository.findByClientAndLoteAndType(
                    client, lote, type).isPresent();
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
