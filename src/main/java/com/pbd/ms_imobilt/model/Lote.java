package com.pbd.ms_imobilt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Table(name = "lote")
@Getter
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 100)
    private String description;
    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;
}
