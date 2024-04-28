package com.pbd.ms_imobilt.lote.repository;

import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.lote.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoteRepositoryI extends JpaRepository<Lote, Integer> {
    Optional<Lote> findByDescriptionAndBlock(String description, Block block);


}
