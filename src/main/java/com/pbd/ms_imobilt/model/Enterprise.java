package com.pbd.ms_imobilt.model;

import jakarta.persistence.*;

import lombok.Getter;

@Entity
@Getter
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
}
