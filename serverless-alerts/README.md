# Módulo de Alertas Serverless (FaaS)

Este componente forma parte de la arquitectura distribuida del sistema "Donaton". Su propósito es procesar eventos críticos de donaciones de manera asíncrona mediante AWS Lambda, garantizando una respuesta inmediata ante emergencias.

## Arquitectura
El microservicio ms-donaciones actúa como productor, clasificando las donaciones y enviando a esta función solo aquellas que contienen ítems críticos (ej. medicamentos).

## Despliegue

### Instrucciones de empaquetado
Para preparar la función para su despliegue en la nube, ejecute el script de empaquetado incluido:

```
chmod +x build.sh
./build.sh
```

Esto generará el archivo lambda-alertas.zip, el cual puede ser cargado directamente en la consola de AWS Lambda o a través del pipeline de CI/CD.

## Pruebas
Trigger: La función está configurada para activarse ante mensajes en la cola SQS cola-alertas-urgencia.

Validación: Tras procesar un mensaje con ítems de categoría MEDICAMENTO, la función registrará en los logs de Amazon CloudWatch el evento de alerta, simulando el envío de notificaciones externas.