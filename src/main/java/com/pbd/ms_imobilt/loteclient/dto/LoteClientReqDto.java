package com.pbd.ms_imobilt.loteclient.dto;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.loteclient.model.Type;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoteClientReqDto (
        @NotNull
        Client client,
        @NotNull
        Lote lote,

        @NotNull
        Type type,

        @NotNull
        LocalDateTime create_at


){
}
