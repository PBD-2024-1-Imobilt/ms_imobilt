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

    boolean existsById(Integer id);

    Optional<LoteClient> findByLote(Lote lote);

    Optional<LoteClient> findByClientAndLoteAndType(Client client, Lote lote, Type type);

    @Modifying
    @Query(value = "UPDATE lote_client SET type = 'CANCEL', observation = :observation WHERE id = :id",
            nativeQuery = true)
    void loteClientCancel(Integer id, String observation);

    void deleteLoteClientById(Integer id);
}