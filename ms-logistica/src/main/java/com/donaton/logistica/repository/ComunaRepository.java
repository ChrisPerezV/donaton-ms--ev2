package com.donaton.logistica.repository;

import com.donaton.logistica.model.entity.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
}