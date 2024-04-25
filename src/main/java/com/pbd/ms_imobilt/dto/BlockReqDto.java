package com.pbd.ms_imobilt.dto;

import com.pbd.ms_imobilt.model.Enterprise;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BlockReqDto(@NotNull Enterprise enterprise,
                          @NotBlank String description) {
}
