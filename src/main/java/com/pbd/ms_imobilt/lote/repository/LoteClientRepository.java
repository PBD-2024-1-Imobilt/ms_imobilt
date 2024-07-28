package com.pbd.ms_imobilt.lote.repository;

import com.pbd.ms_imobilt.client.model.Client;
import com.pbd.ms_imobilt.lote.model.Lote;
import com.pbd.ms_imobilt.lote.model.LoteClient;
import com.pbd.ms_imobilt.lote.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoteClientRepository extends JpaRepository<LoteClient, Integer> {

    Optional<LoteClient> findLoteClientByLote_Id(Integer id);

    Optional<LoteClient> findByClientAndLoteAndType(Client client, Lote lote, Type type);

    @Modifying
    @Query(value = "UPDATE lote_client SET type = 'CANCEL' WHERE id = ?1", nativeQuery = true)
    void loteClientCancel(Integer id);

    boolean deleteLoteClient(Integer id);
}