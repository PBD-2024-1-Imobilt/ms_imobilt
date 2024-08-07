package com.pbd.ms_imobilt.infra.security;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.enterprise.dto.InputBlocksAndLotesReqDto;
import com.pbd.ms_imobilt.lote.dto.InputLoteReqDto;
import com.pbd.ms_imobilt.lote.exception.DuplicateLoteClientException;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import com.pbd.ms_imobilt.lote.model.Type;
import com.pbd.ms_imobilt.lote.repository.LoteClientRepository;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.token.service.TokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ValidationService {

    private final TokenAuthenticationService authToken;

    private final LoteClientRepository loteClientRepository;

    public List<String> validateBlocks(List<InputBlocksAndLotesReqDto> listBlocks){
        if (authToken.validateToken(TokenHearder.token)){
            List<String> erros = new ArrayList<>(),
                    blocks = new ArrayList<>(),
                    lotes = new ArrayList<>();
            for (InputBlocksAndLotesReqDto block : listBlocks){
                if (!blocks.stream().filter(b -> b.replace(" ", "")
                        .equalsIgnoreCase(block.description().replace(" ", ""))).toList().isEmpty())
                    erros.add("Não pode existir quadras com a mesma descrição [%s]"
                            .formatted(block.description()));
                else
                    blocks.add(block.description());

                for (InputLoteReqDto lote : block.lotes())
                    if (!lotes.stream().filter(l -> l.replace(" ", "")
                            .equalsIgnoreCase(lote.description().replace(" ", ""))).toList().isEmpty())
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

    public boolean isLoteSaleOrReserved(Client client, Lote lote, Type type){
        Optional<LoteClient> loteClientOld = loteClientRepository.findByClientAndLote(client, lote);
        if (loteClientOld.isPresent())
            if (loteClientOld.get().getType() == type)
                throw new DuplicateLoteClientException(
                        ("Lote already %s by this client in %s")
                                .formatted(type.getValue(), loteClientOld.get().getCreateAt()), HttpStatus.BAD_REQUEST);
        return loteClientRepository.findByLote(lote)
                .stream().anyMatch(l -> l.getType() == type);
    }




}
