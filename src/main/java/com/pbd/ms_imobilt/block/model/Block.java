package com.pbd.ms_imobilt.block.model;

import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 100)
    private String description;
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
}
