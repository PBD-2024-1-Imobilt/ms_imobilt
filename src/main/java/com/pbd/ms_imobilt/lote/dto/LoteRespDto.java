package com.pbd.ms_imobilt.lote.dto;

import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoteRespDto(
        @NotNull Integer id,
        @NotBlank String lote,
        @NotNull Enterprise enterprise,
        @NotBlank String block
        ) {}
