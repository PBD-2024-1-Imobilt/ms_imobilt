package com.pbd.ms_imobilt.service;

import com.pbd.ms_imobilt.infra.security.TokenAuthenticationService;
import com.pbd.ms_imobilt.infra.security.TokenHearder;
import com.pbd.ms_imobilt.model.Enterprise;
import com.pbd.ms_imobilt.repository.EnterpriseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@AllArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    private final TokenAuthenticationService authToken;

    public Enterprise getEnterpriseByIDService(Integer id){
        return enterpriseRepository.findById(id).get();
    }

    @Transactional
    public Enterprise saveService(Enterprise enterprise){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return enterpriseRepository.save(enterprise);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

}
