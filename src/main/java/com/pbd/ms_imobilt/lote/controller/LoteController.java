package com.pbd.ms_imobilt.lote.controller;

import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.token.model.TokenHearder;
import com.pbd.ms_imobilt.lote.service.LoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class LoteController {

    private final LoteService loteService;

    @GetMapping("/lotes")
    public ResponseEntity<Map<String, List<LoteRespDto>>> getAllLotes(@RequestHeader("token") String tokenHearder){
        TokenHearder.setToken(tokenHearder);
        return ResponseEntity.ok(loteService.getAllLotesService());
    }
}
