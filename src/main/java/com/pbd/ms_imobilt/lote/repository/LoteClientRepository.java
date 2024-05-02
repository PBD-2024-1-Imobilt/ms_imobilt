package com.pbd.ms_imobilt.lote.repository;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import com.pbd.ms_imobilt.lote.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoteClientRepository extends JpaRepository<LoteClient, Integer> {

    Optional<LoteClient> findByClientAndLoteAndType(Client client, Lote lote, Type type);
}