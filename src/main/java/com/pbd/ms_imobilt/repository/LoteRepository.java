package com.pbd.ms_imobilt.repository;

import com.pbd.ms_imobilt.model.Block;
import com.pbd.ms_imobilt.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
    Optional<Lote> findByDescriptionAndBlock(String description, Block block);


}
