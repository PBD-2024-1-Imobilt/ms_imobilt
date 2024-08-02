package com.pbd.ms_imobilt.lote.service;

import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.configuration.TemplateConfig;
import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.lote.exception.LoteNotFoundException;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.repository.LoteRepositoryI;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoteService {

    @Autowired
    private  LoteRepositoryI loteRepository;

    @Autowired
    private  TokenAuthenticationService authToken;

    @Autowired
    private TemplateConfig templateConfig;

    public Optional<Lote> findByDescriptionAndBlock(String description, Block block){
        return loteRepository.findByDescriptionAndBlock(description, block);
    }

    public Lote findByID(int id){
        return loteRepository.findById(id).orElseThrow(
                () -> new LoteNotFoundException("Lote not found !", HttpStatus.BAD_REQUEST)
        );
    }



    public Map<String, List<LoteRespDto>> getAllLotes(){
        Map<String, List<LoteRespDto>> map = new HashMap<>();
        map.put(
                "response", loteRepository
                        .findAll()
                        .stream()
                        .filter(lote -> authToken.validateToken(TokenHearder.token))
                        .map(lote -> templateConfig.formatterLote(lote))
                        .toList()
        );

        if (map.get("response").isEmpty())
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        return map;
    }

    @Transactional
    public void saveLote(Lote lote){
        if (authToken.validateToken(TokenHearder.token)) {
            loteRepository.save(lote);
            return;
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
