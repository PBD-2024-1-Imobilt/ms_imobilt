package com.pbd.ms_imobilt.enterprise.dto;

import com.pbd.ms_imobilt.lote.dto.InputLoteReqDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record InputBlocksAndLotesReqDto(
        @NotBlank
        String description,

        @NotNull
        List<InputLoteReqDto> lotes
) {}
