package com.donaton.logistica.service;

import com.donaton.logistica.dto.DonacionSqsEvent;
import com.donaton.logistica.dto.InventarioRequest;
import com.donaton.logistica.dto.ItemDonacionSqsEvent;
import com.donaton.logistica.model.enums.CategoriaItem;
import com.donaton.logistica.model.enums.UnidadMedida;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message; // <- Importante usar esta importación
import org.springframework.stereotype.Service;

@Service
public class DonacionSqsListener {

    @Autowired
    private InventarioService inventarioService;

    // Al pedir un Message<String>, bloqueamos la auto-conversión de clases ajenas
    @SqsListener("${donaton.sqs.cola-donaciones}")
    public void procesarDonacionRecibida(Message<String> mensajeAWS) {
        // Extraemos el texto puro del mensaje
        String mensajeJsonCrudo = mensajeAWS.getPayload();

        System.out.println("Mensaje recibido desde AWS SQS: " + mensajeJsonCrudo);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            DonacionSqsEvent donacionEvent = mapper.readValue(mensajeJsonCrudo, DonacionSqsEvent.class);

            if (donacionEvent.getIdCentroAcopio() != null && !donacionEvent.getIdCentroAcopio().trim().isEmpty()) {

                for (ItemDonacionSqsEvent itemEvent : donacionEvent.getItems()) {
                    try {
                        InventarioRequest request = new InventarioRequest();
                        request.setIdCentro(donacionEvent.getIdCentroAcopio());
                        request.setCategoria(CategoriaItem.valueOf(itemEvent.getCategoria()));
                        request.setCantidad(itemEvent.getCantidad());
                        request.setUnidadMedida(UnidadMedida.valueOf(itemEvent.getUnidadMedida()));

                        inventarioService.agregarStock(request);
                        System.out.println("Stock sumado en BD para: " + itemEvent.getCategoria());

                    } catch (Exception e) {
                        System.err.println("Error agregando stock del ítem: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("La donación recibida no tiene centro de acopio. Se ignora.");
            }

        } catch (Exception e) {
            System.err.println("Error traduciendo el JSON de AWS: " + e.getMessage());
        }
    }
}