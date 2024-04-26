package com.pbd.ms_imobilt.block.dto;

import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BlockReqDto(@NotNull Enterprise enterprise,
                          @NotBlank String description) {
}
