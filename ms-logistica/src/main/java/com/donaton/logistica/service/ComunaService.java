package com.donaton.logistica.service;

import com.donaton.logistica.model.entity.Comuna;
import com.donaton.logistica.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository repository;

    public List<Comuna> obtenerTodas() {
        return repository.findAll();
    }
}