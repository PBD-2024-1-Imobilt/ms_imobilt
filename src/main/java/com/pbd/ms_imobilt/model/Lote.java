package com.pbd.ms_imobilt.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "lote")
@Getter
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private String lote;
    private String block;
}
