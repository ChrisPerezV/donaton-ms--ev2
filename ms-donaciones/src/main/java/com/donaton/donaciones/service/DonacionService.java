package com.donaton.donaciones.service;

import com.donaton.donaciones.client.UsuariosClient;
import com.donaton.donaciones.dto.DonacionRequest;
import com.donaton.donaciones.dto.ItemDonacionRequest;
import com.donaton.donaciones.model.entity.Donacion;
import com.donaton.donaciones.model.entity.ItemDonacion;
import com.donaton.donaciones.model.enums.UnidadMedida;
import com.donaton.donaciones.repository.DonacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository repository;

    @Autowired
    private UsuariosClient usuariosClient;

    @Autowired
    private SqsTemplate sqsTemplate;


    @Value("${donaton.sqs.cola-donaciones}")
    private String nombreCola;
    @Value("${donaton.sqs.cola-alertas}")
    private String nombreColaAlertas;

    public Donacion crearConItems(DonacionRequest request) {
        try {
            usuariosClient.obtenerUsuarioPorId(request.getIdDonante());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: El usuario donante (ID: " + request.getIdDonante() + ") no existe en el sistema.");
        }

        Donacion donacion = new Donacion();
        donacion.setIdDonante(request.getIdDonante());
        donacion.setTipoDonacion(request.getTipoDonacion());
        donacion.setIdCentroAcopio(request.getIdCentroAcopio());

        for (ItemDonacionRequest itemDto : request.getItems()) {
            ItemDonacion item = new ItemDonacion();
            item.setCategoria(itemDto.getCategoria());
            item.setDescripcionItem(itemDto.getDescripcionItem());
            item.setCantidad(itemDto.getCantidad());
            item.setUnidadMedida(itemDto.getUnidadMedida() != null ? itemDto.getUnidadMedida() : UnidadMedida.UNIDADES);

            donacion.agregarItem(item);
        }

        Donacion donacionGuardada = repository.save(donacion);

        if (request.getIdCentroAcopio() != null && !request.getIdCentroAcopio().trim().isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                String donacionJsonPuro = mapper.writeValueAsString(donacionGuardada);

                sqsTemplate.send(nombreCola, donacionJsonPuro);
                boolean requiereAlerta = request.getItems().stream()
                        .anyMatch(item -> item.getCategoria().name().equals("MEDICAMENTOS"));

                if (requiereAlerta) {
                    sqsTemplate.send(nombreColaAlertas, donacionJsonPuro);
                    System.out.println("Evento crítico enviado a la cola de Serverless SQS.");
                }

                System.out.println("Evento enviado a SQS exitosamente. ID Donación: " + donacionGuardada.getId());
            } catch (Exception e) {
                System.err.println("Error enviando el mensaje a AWS SQS: " + e.getMessage());
            }
        }

        return donacionGuardada;
    }

    public List<Donacion> obtenerTodas() {
        return repository.findAll();
    }

    public List<Donacion> obtenerDonacionesPorDonante(String idDonante) {
        return repository.findByIdDonante(idDonante);
    }
}