package com.pbd.ms_imobilt.infra.security;

import lombok.Setter;

public class TokenHearder {

    public static String token;

    public static void setToken(String token) {
        TokenHearder.token = token;
    }
}
