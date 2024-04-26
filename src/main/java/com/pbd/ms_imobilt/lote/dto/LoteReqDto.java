package com.pbd.ms_imobilt.lote.dto;

import com.pbd.ms_imobilt.block.model.Block;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoteReqDto(
        @NotNull Block block,
        @NotBlank String description
        ) {
}
