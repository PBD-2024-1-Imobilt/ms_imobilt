package com.pbd.ms_imobilt.block.service;

import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.block.repository.BlockRepositoryI;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlockService {

    private final BlockRepositoryI blockRepository;

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
