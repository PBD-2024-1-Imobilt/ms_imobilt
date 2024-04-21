package com.pbd.ms_imobilt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BlockReqDto(
        @NotBlank
        String description,

        @NotNull
        List<LoteReqDto> lotes
) {}
