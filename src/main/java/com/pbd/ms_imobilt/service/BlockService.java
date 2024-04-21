package com.pbd.ms_imobilt.service;

import com.pbd.ms_imobilt.infra.security.TokenAuthenticationService;
import com.pbd.ms_imobilt.model.Block;
import com.pbd.ms_imobilt.repository.BlockRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@AllArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;

    private final TokenAuthenticationService authToken;

    @Transactional
    public Block saveService(String tokenHeader, Block block){
        if (authToken.tokenHearderValidation(tokenHeader))
            return blockRepository.save(block);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }



}
