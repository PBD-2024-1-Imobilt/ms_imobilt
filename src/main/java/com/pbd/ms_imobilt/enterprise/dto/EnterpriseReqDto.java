package com.pbd.ms_imobilt.enterprise.dto;

import jakarta.validation.constraints.NotBlank;

public record EnterpriseReqDto(@NotBlank String description) {
}
