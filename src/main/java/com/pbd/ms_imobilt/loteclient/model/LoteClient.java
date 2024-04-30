package com.pbd.ms_imobilt.loteclient.model;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.model.Lote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lote_client")
public class LoteClient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    private Type type;

    @Column(name = "create_at")
    private LocalDateTime createAt;

}