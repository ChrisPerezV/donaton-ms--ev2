package com.donaton.logistica.repository;

import com.donaton.logistica.model.entity.DespachoTrazabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoRepository extends JpaRepository<DespachoTrazabilidad, String> {
}