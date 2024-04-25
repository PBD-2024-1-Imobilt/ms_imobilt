package com.pbd.ms_imobilt.dto;

import com.pbd.ms_imobilt.model.Block;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoteReqDto(
        @NotNull Block block,
        @NotBlank String description
        ) {
}
