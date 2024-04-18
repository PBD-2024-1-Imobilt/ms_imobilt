package com.pbd.ms_imobilt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    private String description;
}
