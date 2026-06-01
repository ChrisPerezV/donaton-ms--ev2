package com.donaton.logistica.repository;

import com.donaton.logistica.model.entity.CentroAcopio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.donaton.logistica.model.entity.Inventario;

import java.util.List;

@Repository
public interface CentroAcopioRepository extends JpaRepository<CentroAcopio, String> {

}