package com.pbd.ms_imobilt.dto;

import jakarta.validation.constraints.NotBlank;


public record TokenRequestDto(@NotBlank String token){

} 
