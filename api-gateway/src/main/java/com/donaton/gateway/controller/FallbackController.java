package com.donaton.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/necesidades")
    public Map<String, String> necesidadesFallback() {
        Map<String, String> res = new HashMap<>();
        res.put("mensaje", "Servicio de Necesidades no disponible.");
        return res;
    }
}