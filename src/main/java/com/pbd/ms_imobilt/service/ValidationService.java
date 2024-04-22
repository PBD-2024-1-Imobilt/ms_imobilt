package com.pbd.ms_imobilt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pbd.ms_imobilt.dto.BlockReqDto;
import com.pbd.ms_imobilt.dto.LoteReqDto;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ValidationService {

    public List<String> validateBlocks(List<BlockReqDto> listBlocks){
        List<String> erros = new ArrayList<>(),
                blocks = new ArrayList<>(),
                lotes = new ArrayList<>();
        for (BlockReqDto block : listBlocks){
            if (blocks.contains(block.description()))
                erros.add("Não pode existir quadras com a mesma descrição [%s]"
                        .formatted(block.description()));
            else
                blocks.add(block.description());

            for (LoteReqDto lote : block.lotes())
                if (lotes.contains(lote.description()))
                    erros.add("Não pode existir lotes com a mesma descrição [%s] na quadra [%s]"
                    .formatted(lote.description(), block.description()));

                else
                    lotes.add(lote.description());

            lotes.clear();
        }
        return erros;
    }
}
