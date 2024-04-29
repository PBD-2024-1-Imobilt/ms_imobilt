package com.pbd.ms_imobilt.client.service;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.client.repository.ClientRepositoryI;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepositoryI clientRepositoryI;
    private TokenAuthenticationService authToken;

    public Optional<Client> findByIdService(Integer id){
        return clientRepositoryI.findById(id);
    }

    @Transactional
    public Client saveService(Client client){
        if (authToken.tokenHearderValidation(TokenHearder.token))
            return clientRepositoryI.save(client);
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

}
