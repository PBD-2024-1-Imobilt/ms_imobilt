package com.pbd.ms_imobilt.lote.service;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.dto.LoteClientReqDto;
import com.pbd.ms_imobilt.lote.dto.ObservationReqDto;
import com.pbd.ms_imobilt.lote.exception.LoteCiientCancelException;
import com.pbd.ms_imobilt.lote.exception.LoteClientFailedDeleteException;
import com.pbd.ms_imobilt.lote.exception.LoteClientNotFound;
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
import java.util.List;

@Service
public class LoteClientService {

    @Autowired
    private  LoteClientRepository loteClientRepository;

    @Autowired
    private  TokenAuthenticationService authToken;

    public List<LoteClient> findByLote(Lote lote){
        List<LoteClient> loteClientList = loteClientRepository.findByLote(lote);
        if (loteClientList.isEmpty())
            throw new LoteClientNotFound("LoteClient not found!",
                    HttpStatus.BAD_REQUEST);
        return loteClientList;
    }

    public boolean isLoteReservedByAnotherClient(Client client, Lote lote){
        List<LoteClient> listloteClientReserved = loteClientRepository.findByLote(lote)
                .stream().filter(l -> l.getType() == Type.RESERVE).toList();

        if (!listloteClientReserved.isEmpty()){
            LoteClient loteClient = listloteClientReserved.stream().findFirst().get();
            return !loteClient.getClient().getId().equals(client.getId());
        }
        return false;
    }

    @Transactional
    public ResponseEntity<RespIdDefaultDto> save(Client client, Lote lote, Type type) {

        LoteClient loteClient = new LoteClient();

        BeanUtils.copyProperties(new LoteClientReqDto(client, lote, type, LocalDateTime.now()), loteClient);

        if (loteClientRepository.existsByLoteAndClient(lote, client)) {

            LoteClient loteClientOld = loteClientRepository.findByClientAndLote(client, lote)
                    .orElseThrow(() -> new LoteClientNotFound("LoteClient not found!",
                            HttpStatus.BAD_REQUEST));

            BeanUtils.copyProperties(new RespIdDefaultDto(loteClientOld.getId()), loteClient);
        }
        if (authToken.validateToken(TokenHearder.token)) {
            loteClient = loteClientRepository.save(loteClient);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RespIdDefaultDto(loteClient.getId()));
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
    @Transactional
    public ResponseEntity<RespIdDefaultDto> loteClientCancel(LoteClient loteClient, ObservationReqDto observartion){
        if (authToken.validateToken(TokenHearder.token)){
            switch (loteClient.getType()){
                case SALE:
                    loteClientRepository.loteClientCancel(loteClient.getId(), observartion.observation());
                    break;
                case RESERVE:
                    loteClientRepository.deleteLoteClientById(loteClient.getId());
                    if (!loteClientRepository.existsById(loteClient.getId()))
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
