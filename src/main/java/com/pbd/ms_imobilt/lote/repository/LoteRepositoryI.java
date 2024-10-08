package com.pbd.ms_imobilt.lote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pbd.ms_imobilt.lote.model.Lote;

public interface LoteRepositoryI extends JpaRepository<Lote, Integer> {

    @Query(value = "SELECT * FROM lote WHERE description ILIKE :description AND block_id=:blockId",
            nativeQuery = true)
    Optional<Lote> findByDescriptionAndBlock(String description, int blockId);


}
