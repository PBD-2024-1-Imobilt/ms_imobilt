package com.pbd.ms_imobilt.repository;

import com.pbd.ms_imobilt.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {
    Optional<Block> findByDescription(String description);
}
