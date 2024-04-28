package com.pbd.ms_imobilt.client.repository;

import com.pbd.ms_imobilt.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositoryI extends JpaRepository<Client, Integer> {
}
