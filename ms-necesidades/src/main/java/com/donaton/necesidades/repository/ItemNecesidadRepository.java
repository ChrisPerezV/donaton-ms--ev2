package com.donaton.necesidades.repository;

import com.donaton.necesidades.model.entity.ItemNecesidad;
import com.donaton.necesidades.model.enums.CategoriaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemNecesidadRepository extends JpaRepository<ItemNecesidad, String> {

    // Método para buscar un ítem específico dentro de una necesidad usando la categoría
    Optional<ItemNecesidad> findByNecesidadIdAndCategoria(String idNecesidad, CategoriaItem categoria);
}