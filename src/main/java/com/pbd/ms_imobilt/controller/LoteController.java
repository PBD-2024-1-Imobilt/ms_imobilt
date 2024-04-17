package com.pbd.ms_imobilt.controller;

import com.pbd.ms_imobilt.model.Lote;
import com.pbd.ms_imobilt.service.LoteService;
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
    public ResponseEntity<Map<String, List<Lote>>> getAllLands(@RequestHeader("token") String tokenSHearder){
        return ResponseEntity.ok(loteService.getAllLandsService(tokenSHearder));
    }
}
