package com.donaton.logistica.repository;

import com.donaton.logistica.model.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, String> {
    // Método para buscar qué hay en una bodega específica
    List<Inventario> findByCentroAcopioId(String idCentro);
}