package com.pbd.ms_imobilt.client.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 200)
    private String name;
    @Size(max = 30)
    private String phone;
    @Size(max = 14)
    private String cpf;
}
