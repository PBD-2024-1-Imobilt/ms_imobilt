package com.pbd.ms_imobilt.enterprise.service;

import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import com.pbd.ms_imobilt.enterprise.repository.EnterpriseRepositoryI;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@AllArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepositoryI enterpriseRepository;

    private final TokenAuthenticationService authToken;

    public Enterprise getEnterpriseByIDService(Integer id){
        return enterpriseRepository.findById(id).get();
    }

    @Transactional
    public Enterprise saveService(Enterprise enterprise){
        if (authToken.validateToken(TokenHearder.token))
            return enterpriseRepository.save(enterprise);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

}
