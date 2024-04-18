package com.pbd.ms_imobilt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record TokenRequestDto(@NotBlank String token){

} 
