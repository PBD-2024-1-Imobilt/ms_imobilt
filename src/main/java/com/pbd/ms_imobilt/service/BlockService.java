package com.pbd.ms_imobilt.service;

import com.pbd.ms_imobilt.infra.security.TokenAuthenticationService;
import com.pbd.ms_imobilt.infra.security.TokenHearder;
import com.pbd.ms_imobilt.model.Block;
import com.pbd.ms_imobilt.repository.BlockRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    private final TokenAuthenticationService authToken;

    public Optional<Block> findByDescriptionService(String description){
        return blockRepository.findByDescription(description);
    }

    @Transactional
    public Block saveService(Block block){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return blockRepository.save(block);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }




}
