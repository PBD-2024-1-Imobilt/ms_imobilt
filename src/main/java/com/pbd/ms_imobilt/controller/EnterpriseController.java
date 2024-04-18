package com.pbd.ms_imobilt.controller;

import com.pbd.ms_imobilt.dto.EnterpriseReqDto;
import com.pbd.ms_imobilt.dto.EnterpriseRespDto;
import com.pbd.ms_imobilt.model.Enterprise;
import com.pbd.ms_imobilt.service.EnterpriseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/enterprise")
@AllArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

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

}
