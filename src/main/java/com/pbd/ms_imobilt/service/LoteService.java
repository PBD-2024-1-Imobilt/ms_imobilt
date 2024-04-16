package com.pbd.ms_imobilt.service;

import com.pbd.ms_imobilt.dto.TokenRequestDto;
import com.pbd.ms_imobilt.model.Lote;
import com.pbd.ms_imobilt.repository.LoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    public Map<String, List<Lote>> getAllLandsService(String tokenSHearder){
        Map<String, List<Lote>> map = new HashMap<>();
        map.put(
                "response", loteRepository
                        .findAll()
                        .stream()
                        .filter(e -> tokenHearderValidation(tokenSHearder))
                        .toList()
        );
        return map;
    }

    private Integer requestByPost(TokenRequestDto tokenRequestDto)  {

        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<TokenRequestDto> entity = new HttpEntity<>(tokenRequestDto, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/vi/authentication/validation",
                HttpMethod.POST, entity, String.class);

        return response.getStatusCode().value();
    }

    private boolean tokenHearderValidation(String tokenHearder){
        return requestByPost(
                new TokenRequestDto(tokenHearder)
        ) == 200;
    }
}
