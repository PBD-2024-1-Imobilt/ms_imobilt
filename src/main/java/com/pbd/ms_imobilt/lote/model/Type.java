package com.pbd.ms_imobilt.lote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {

    RESERVE("RESERVE"),
    SALE("SALE"),
    CANCEL("CANCEL");

    private final String value;
}
