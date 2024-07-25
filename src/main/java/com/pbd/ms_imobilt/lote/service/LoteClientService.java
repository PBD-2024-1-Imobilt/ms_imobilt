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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Service
public class LoteClientService {

    @Autowired
    private  LoteClientRepository loteClientRepository;

    @Autowired
    private  TokenAuthenticationService authToken;

    @Transactional
    public ResponseEntity<RespIdDefaultDto> saveService(Client client, Lote lote, Type type){

        LoteClient loteClient = new LoteClient();

        BeanUtils.copyProperties(new LoteClientReqDto(client, lote, type, LocalDateTime.now()), loteClient);

        if (authToken.validateToken(TokenHearder.token)){
            loteClient = loteClientRepository.save(loteClient);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RespIdDefaultDto(loteClient.getId()));
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    public boolean isLoteClientExists(Client client, Lote lote, Type type){
        if (authToken.validateToken(TokenHearder.token))
            return loteClientRepository.findByClientAndLoteAndType(
                    client, lote, type).isPresent();
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
