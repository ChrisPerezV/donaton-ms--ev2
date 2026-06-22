exports.handler = async (event) => {
    console.log("Función Lambda ejecutada por evento de SQS");

    for (const record of event.Records) {
        try {
            const donacion = JSON.parse(record.body);
            let requiereAlerta = false;

            console.log(`Analizando donación..... ID: ${donacion.id}`);

            donacion.items.forEach(item => {
                if (item.categoria === 'AGUA' || item.categoria === 'MEDICAMENTO' || item.categoria === 'ALIMENTO_NO_PERECIBLE') {
                    requiereAlerta = true;
                }
            });

            if (requiereAlerta) {
                console.log("=========================================");
                console.log("ALERTA DE ALTA PRIORIDAD DETECTADA");
                console.log(`Donación (ID: ${donacion.id}) contiene ítems críticos.`);
                console.log("Simulando envío de SMS a coordinadores: 'RETIRO INMEDIATO REQUERIDO'");
                console.log("Simulando envío de correo a logistica@donaton.org");
                console.log("=========================================");
            } else {
                console.log(`Donación procesada. No contiene ítems críticos. Se omite alerta.`);
            }

        } catch (error) {
            console.error("Error decodificando el mensaje SQS:", error);
        }
    }

    return {
        statusCode: 200,
        body: JSON.stringify('Alertas procesadas exitosamente'),
    };
};