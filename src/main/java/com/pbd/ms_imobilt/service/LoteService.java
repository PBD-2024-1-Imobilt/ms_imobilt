package com.pbd.ms_imobilt.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pbd.ms_imobilt.dto.TokenRequestDto;
import com.pbd.ms_imobilt.model.Lote;
import com.pbd.ms_imobilt.repository.LoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;

    public Map<String, List<Lote>> getAllLandsService(String tokenSHearder){
        Map<String, List<Lote>> map = new HashMap<>();
        map.put(
                "response", loteRepository
                        .findAll()
                        .stream()
                        .filter(e -> validationTokenSHearder(tokenSHearder))
                        .toList()
        );
        return map;
    }

    private Integer requestByPost(String uri, Object objBody) throws URISyntaxException, IOException, InterruptedException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

       HttpPost request = new HttpPost(uri);

        String json = new Gson().toJson(objBody);

        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

        request.setEntity(stringEntity);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.executeOpen(null, request, null);

       return response.getCode();
    }

    private boolean validationTokenSHearder(String tokenSHearder){
        try {
            return requestByPost(
                    "http://localhost:8080/api/vi/authentication/validation",
                    new TokenRequestDto(tokenSHearder)
            ) == 200;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }




}
