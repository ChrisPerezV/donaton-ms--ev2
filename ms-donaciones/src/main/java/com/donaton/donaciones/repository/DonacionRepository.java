package com.donaton.donaciones.repository;

import com.donaton.donaciones.model.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, String> {
}