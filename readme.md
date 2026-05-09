Requisitos Previos

Para ejecutar este proyecto localmente, asegurar de tener instalado:

- Java 21
- Maven 3.8+
- Docker Desktop
- MySQL Server
- Postman

Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Spring Data JPA / Hibernate
- MySQL
- Lombok
- Resilience4j

Instalación y Configuración

1. Clonar el repositorio

- Abriendo una consola de windows (CMD)
- git clone https://github.com/ChrisPerezV/donaton-ms--ev2.git
- cd donaton-ms--ev2

2. Creación de BD

Para levantar las bases de datos de forma automática, asegúrese de tener Docker Desktop iniciado y ejecute:

- Verificar funcionamiento de docker en el equipo
- Dirigirse a la ruta en la cual se clonó el proyecto:
- cd donaton-ms--ev2
- Dirigirse a carpeta de configuración de docker:
- cd donaton-infra
- Ejecutar comando:
- docker-compose up -d
- Se incializan contenedores docker con Mysql con bases de datos para el proyecto

3. Compilación del Proyecto
Ejecute el siguiente comando en la raíz de cada microservicio o en la carpeta principal:

- Abriendo una consola de windows(CMD)
- mvnw clean install

Orden de Ejecución
- Para asegurar que la comunicación entre servicios funcione correctamente, inicie los componentes en este orden:

- API Gateway (Puerto 8080)

- MS Necesidades (Puerto 8082)

- MS Logística (Puerto 8084)

- MS Donaciones (Puerto 8083)

4. Pruebas de Endpoints (Postman)
Todas las peticiones deben dirigirse al API Gateway (8080).

- Crear una Necesidad
POST http://localhost:8080/api/v1/necesidades

JSON
{
    "idUsuarioCreador": "admin123",
    "tituloEmergencia": "Incendio en Valparaíso",
    "idComuna": 5101,
    "direccionEspecifica": "Cerro San Roque",
    "items": [
        {
            "categoria": "AGUA",
            "descripcionItem": "Agua embotellada 1.5L",
            "cantidadRequerida": 100,
            "unidadMedida": "UNIDADES",
            "prioridad": "ALTA_URGENCIA"
        }
    ]
}
- Registrar Centro de Acopio y Cargar Stock
- POST http://localhost:8080/api/v1/logistica/centros-acopio

- POST http://localhost:8080/api/v1/logistica/inventario/cargar

- Registrar Donación (Entrada a Bodega)
- POST http://localhost:8080/api/v1/donaciones

JSON
{
    "idDonante": "12345678-9",
    "tipoDonacion": "ESPECIE",
    "idCentroAcopio": "ID_DEL_CENTRO",
    "items": [
        {
            "categoria": "AGUA",
            "descripcionItem": "Pack de 6 aguas",
            "cantidad": 50,
            "unidadMedida": "UNIDADES"
        }
    ]
}
- Crear Despacho y Entrega (Cierre del Ciclo)
- POST http://localhost:8080/api/v1/logistica/despachos

- PUT http://localhost:8080/api/v1/logistica/despachos/{id}/estado?nuevoEstado=ENTREGADO