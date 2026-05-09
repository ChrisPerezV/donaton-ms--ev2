package com.donaton.donaciones.repository;

import com.donaton.donaciones.model.entity.ItemDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDonacionRepository extends JpaRepository<ItemDonacion, String> {
}