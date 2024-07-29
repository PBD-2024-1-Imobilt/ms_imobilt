package com.pbd.ms_imobilt.lote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.pbd.ms_imobilt.configuration.TemplateConfig;
import com.pbd.ms_imobilt.exception.LoteNotFoundException;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.repository.LoteRepositoryI;

@Service
public class LoteService {

    @Autowired
    private  LoteRepositoryI loteRepository;

    @Autowired
    private  TokenAuthenticationService authToken;

    @Autowired
    private TemplateConfig templateConfig;

    public Optional<Lote> findByDescriptionAndBlockService(String description, Block block){
        return loteRepository.findByDescriptionAndBlock(description, block);
    }

    public Lote findByIDService(int id){
        return loteRepository.findById(id).orElseThrow(
                () -> new LoteNotFoundException("Lote not found !", HttpStatus.BAD_REQUEST)
        );
    }



    public Map<String, List<LoteRespDto>> getAllLotesService(){
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
    public void saveLoteService(Lote lote){
        if (authToken.validateToken(TokenHearder.token)) {
            loteRepository.save(lote);
            return;
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
