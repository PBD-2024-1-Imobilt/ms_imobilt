package com.pbd.ms_imobilt.lote.model;

import com.pbd.ms_imobilt.block.model.Block;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lote")
@Getter
@Setter
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
