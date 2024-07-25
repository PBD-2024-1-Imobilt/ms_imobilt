package com.pbd.ms_imobilt.token.components;

import com.pbd.ms_imobilt.token.dto.TokenRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidationApi {

    @Autowired
    private RestTemplate restTemplate;


    public Integer requestTokenValidationStatusCode(String uri, TokenRequestDto tokenRequestDto){

        ResponseEntity<String> response = restTemplate.postForEntity(uri, tokenRequestDto, String.class);

        return response.getStatusCode().value();
    }
}
