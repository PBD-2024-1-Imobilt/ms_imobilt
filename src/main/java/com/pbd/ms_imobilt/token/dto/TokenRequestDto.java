package com.pbd.ms_imobilt.token.dto;

import jakarta.validation.constraints.NotBlank;


public record TokenRequestDto(@NotBlank String token){

} 
