package com.pbd.ms_imobilt.client.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientReqDto(
        @NotBlank
        String name,

        @NotBlank
        String phone,

        @NotBlank
        String cpf
) {
}
