package com.pbd.ms_imobilt.infra.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pbd.ms_imobilt.dto.InputBlocksAndLotesReqDto;
import com.pbd.ms_imobilt.dto.InputLoteReqDto;

import lombok.AllArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;

@Service
@AllArgsConstructor
public class ValidationService {

    private final TokenAuthenticationService authToken;

    public List<String> validateBlocks(List<InputBlocksAndLotesReqDto> listBlocks){
        if (authToken.tokenHearderValidation(TokenHearder.token)){
            List<String> erros = new ArrayList<>(),
                    blocks = new ArrayList<>(),
                    lotes = new ArrayList<>();
            for (InputBlocksAndLotesReqDto block : listBlocks){
                if (blocks.contains(block.description()))
                    erros.add("Não pode existir quadras com a mesma descrição [%s]"
                            .formatted(block.description()));
                else
                    blocks.add(block.description());

                for (InputLoteReqDto lote : block.lotes())
                    if (lotes.contains(lote.description()))
                        erros.add("Não pode existir lotes com a mesma descrição [%s] na quadra [%s]"
                                .formatted(lote.description(), block.description()));

                    else
                        lotes.add(lote.description());

                lotes.clear();
            }
            return erros;
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }
}
