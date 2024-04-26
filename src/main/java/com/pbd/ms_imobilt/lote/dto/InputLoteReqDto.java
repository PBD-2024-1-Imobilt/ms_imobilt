package com.pbd.ms_imobilt.lote.dto;

import jakarta.validation.constraints.NotBlank;

public record InputLoteReqDto(@NotBlank String description) {
}
