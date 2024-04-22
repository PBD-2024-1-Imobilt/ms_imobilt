package com.pbd.ms_imobilt.controller;

import com.pbd.ms_imobilt.dto.BlockReqDto;
import com.pbd.ms_imobilt.dto.EnterpriseReqDto;
import com.pbd.ms_imobilt.dto.EnterpriseRespDto;
import com.pbd.ms_imobilt.model.Enterprise;
import com.pbd.ms_imobilt.service.EnterpriseService;
import com.pbd.ms_imobilt.service.ValidationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/enterprise")
@AllArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    private final ValidationService validationService;

    @PostMapping
    public ResponseEntity<EnterpriseRespDto> postCreateEnterprise(@RequestHeader(name = "token") String tokenHeader, @RequestBody @Valid EnterpriseReqDto requestBody){
        Enterprise enterprise = new Enterprise();
        BeanUtils.copyProperties(requestBody, enterprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new EnterpriseRespDto(
                        enterpriseService.saveService(
                                tokenHeader,
                                enterprise
                        ).getId()
                )
        );
    }

    @PostMapping("{enterprise_id}/blocks")
    public ResponseEntity<List<String>> postCreateBlocks(
            @PathVariable(name = "enterprise_id") Integer enterpriseId ,
            @RequestBody List<BlockReqDto> listBlocks){

                return ResponseEntity.status(HttpStatus.CREATED).body(validationService.validateBlocks(listBlocks));

    }

}
