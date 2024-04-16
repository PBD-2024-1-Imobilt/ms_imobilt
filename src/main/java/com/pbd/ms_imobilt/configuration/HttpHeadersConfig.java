package com.pbd.ms_imobilt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class HttpHeadersConfig {

    @Bean
    public HttpHeaders httpHeaders(){
        return new HttpHeaders();
    }
}
