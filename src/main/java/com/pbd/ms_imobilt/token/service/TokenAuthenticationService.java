package com.pbd.ms_imobilt.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pbd.ms_imobilt.token.components.ValidationApi;
import com.pbd.ms_imobilt.token.dto.TokenRequestDto;
@Service
public class TokenAuthenticationService {

    @Value("${URI}")
    private String URI;

    @Autowired
    private ValidationApi validationApiService;

    public boolean validateToken(String tokenHearder){
        return validationApiService
                .requestTokenValidationStatusCode(
                        URI, new TokenRequestDto(tokenHearder)
                ) == 200;
    }

}

