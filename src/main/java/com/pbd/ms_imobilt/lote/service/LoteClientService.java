package com.pbd.ms_imobilt.lote.service;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.exception.LoteCiientCancelException;
import com.pbd.ms_imobilt.exception.LoteClientFailedDeleteException;
import com.pbd.ms_imobilt.exception.LoteClientNotFound;
import com.pbd.ms_imobilt.lote.dto.LoteClientReqDto;
import com.pbd.ms_imobilt.lote.dto.ObservationReqDto;
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

    public LoteClient findByLoteService(Lote lote){
        return loteClientRepository.findByLote(lote)
                .orElseThrow(() -> new LoteClientNotFound("LoteClient not found!",
                        HttpStatus.BAD_REQUEST));
    }

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

    public ResponseEntity<RespIdDefaultDto> loteClientCancelService(LoteClient loteClient, ObservationReqDto observartion){
        if (authToken.validateToken(TokenHearder.token)){
            switch (loteClient.getType()){
                case SALE:
                    loteClientRepository.loteClientCancel(loteClient.getId(), observartion.observation());
                    break;
                case RESERVE:
                    if (loteClientRepository.deleteLoteClientById(loteClient.getId()))
                        break;
                    throw new LoteClientFailedDeleteException("Failed to delete", HttpStatus.INTERNAL_SERVER_ERROR);
                case CANCEL:
                    throw new LoteCiientCancelException("This loteClient has already been cancelled",
                            HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(
                    new RespIdDefaultDto(loteClient.getId())
            );
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }


}
