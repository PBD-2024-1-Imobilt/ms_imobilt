package com.pbd.ms_imobilt.block.repository;

import com.pbd.ms_imobilt.block.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepositoryI extends JpaRepository<Block, Integer> {
    Optional<Block> findByDescription(String description);
}
