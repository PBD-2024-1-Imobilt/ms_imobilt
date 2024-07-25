package com.pbd.ms_imobilt.enterprise.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.pbd.ms_imobilt.token.dto.TokenRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbd.ms_imobilt.block.dto.BlockReqDto;
import com.pbd.ms_imobilt.enterprise.dto.EnterpriseReqDto;
import com.pbd.ms_imobilt.enterprise.dto.EnterpriseRespDto;
import com.pbd.ms_imobilt.enterprise.dto.InputBlocksAndLotesReqDto;
import com.pbd.ms_imobilt.lote.dto.InputLoteReqDto;
import com.pbd.ms_imobilt.lote.dto.LoteReqDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.infra.security.ValidationService;
import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.block.service.BlockService;
import com.pbd.ms_imobilt.enterprise.service.EnterpriseService;
import com.pbd.ms_imobilt.lote.service.LoteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/enterprise")
@AllArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    private final BlockService blockService;

    private final LoteService loteService;

    private final ValidationService validationService;

    @PostMapping
    public ResponseEntity<EnterpriseRespDto> postCreateEnterprise(@RequestHeader(name = "token") String tokenHeader,
                                                                  @RequestBody @Valid EnterpriseReqDto requestBody){
        TokenHearder.token = tokenHeader;
        Enterprise enterprise = new Enterprise();
        BeanUtils.copyProperties(requestBody, enterprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new EnterpriseRespDto(
                        enterpriseService.saveService(enterprise).getId()
                )
        );
    }

    @PostMapping("{enterprise_id}/blocks")
    public ResponseEntity<?> postCreateBlocks(
            @RequestHeader(name = "token") String tokenHeader,
            @PathVariable(name = "enterprise_id") Integer enterpriseId ,
            @RequestBody  @Valid List<InputBlocksAndLotesReqDto> listBlocks){

        TokenHearder.token = tokenHeader;

        List<String> validateBlocksError = validationService.validateBlocks(listBlocks);

        if (validateBlocksError.isEmpty()){
            Enterprise enterprise = enterpriseService.getEnterpriseByIDService(enterpriseId);
            for (InputBlocksAndLotesReqDto itemBlock: listBlocks){

                Block block = new Block();

                BeanUtils.copyProperties(new BlockReqDto(enterprise, itemBlock.description()), block);

                Optional<Block> blockExists = blockService.findByDescriptionService(itemBlock.description());

                block = blockExists.isPresent() ? blockExists.get():
                        blockService.saveService(block);

                for (InputLoteReqDto inputLote: itemBlock.lotes()){
                    Lote lote = new Lote();

                    BeanUtils.copyProperties(new LoteReqDto(block, inputLote.description()), lote);

                    if (loteService.findByDescriptionAndBlockService(inputLote.description(), block).isEmpty())
                        loteService.saveLoteService(lote);
                }
            }

                return ResponseEntity.status(HttpStatus.CREATED).body(Collections.emptyMap());
        }

        return ResponseEntity.badRequest().body(validateBlocksError);

    }

}
