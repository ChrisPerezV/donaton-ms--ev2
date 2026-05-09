package com.donaton.logistica.repository;

import com.donaton.logistica.model.entity.CentroAcopio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroAcopioRepository extends JpaRepository<CentroAcopio, String> {
}