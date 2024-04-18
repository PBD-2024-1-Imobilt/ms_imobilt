package com.pbd.ms_imobilt.service;

import com.pbd.ms_imobilt.infra.security.TokenAuthenticationService;
import com.pbd.ms_imobilt.model.Lote;
import com.pbd.ms_imobilt.repository.LoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;

    private final TokenAuthenticationService authToken;

    public Map<String, List<Lote>> getAllLandsService(String tokenHearder){
        Map<String, List<Lote>> map = new HashMap<>();
        map.put(
                "response", loteRepository
                        .findAll()
                        .stream()
                        .filter(e -> authToken.tokenHearderValidation(
                                tokenHearder
                                )
                        )
                        .toList()
        );

        if (map.get("response").isEmpty())
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        return map;
    }
}
