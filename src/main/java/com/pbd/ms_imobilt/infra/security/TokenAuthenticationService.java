package com.pbd.ms_imobilt.infra.security;

import com.pbd.ms_imobilt.dto.TokenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TokenAuthenticationService {

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    private Integer requestByPost(String uri, TokenRequestDto tokenRequestDto)  {

        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<TokenRequestDto> entity = new HttpEntity<>(tokenRequestDto, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(uri,
                HttpMethod.POST, entity, String.class);

        return response.getStatusCode().value();
    }

    public boolean tokenHearderValidation(String tokenHearder){
        return requestByPost(
                "http://localhost:8080/api/vi/authentication/validation",
                new TokenRequestDto(tokenHearder)
        ) == 200;
    }
}
