package com.pbd.ms_imobilt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRequestDto{
    private String token;


    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            "}";
    }

} 
