package com.pbd.ms_imobilt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pbd.ms_imobilt.infra.security.TokenHearder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.pbd.ms_imobilt.dto.LoteRespDto;
import com.pbd.ms_imobilt.infra.security.TokenAuthenticationService;
import com.pbd.ms_imobilt.model.Block;
import com.pbd.ms_imobilt.model.Enterprise;
import com.pbd.ms_imobilt.model.Lote;
import com.pbd.ms_imobilt.repository.BlockRepository;
import com.pbd.ms_imobilt.repository.EnterpriseRepository;
import com.pbd.ms_imobilt.repository.LoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;

    private final BlockRepository blockRepository;

    private final EnterpriseRepository enterpriseRepository;

    private final TokenAuthenticationService authToken;

    public Optional<Lote> findByDescriptionAndBlockService(String description, Block block){
        return loteRepository.findByDescriptionAndBlock(description, block);
    }

    private LoteRespDto formatterLote(Lote lote) {
        Block block = blockRepository.findById(lote.getBlock().getId()).get();

        Enterprise enterprise = enterpriseRepository.findById(
                block.getEnterprise().getId()
        ).get();

        return  new LoteRespDto(
                lote.getId(),
                lote.getDescription(),
                enterprise,
                block.getDescription()
        );

    }

    public Map<String, List<LoteRespDto>> getAllLotesService(){
        Map<String, List<LoteRespDto>> map = new HashMap<>();
        map.put(
                "response", loteRepository
                        .findAll()
                        .stream()
                        .filter(e -> authToken.tokenHearderValidation(TokenHearder.token))
                        .map(this::formatterLote)
                        .toList()
        );

        if (map.get("response").isEmpty())
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        return map;
    }

    @Transactional
    public Lote saveLoteService(Lote lote){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return loteRepository.save(lote);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
