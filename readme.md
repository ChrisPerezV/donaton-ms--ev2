# Proyecto Donaton

## Requisitos Previos
Para ejecutar este proyecto localmente, asegurar de tener instalado:

- Java 21
- Maven 3.8+
- Docker Desktop
- MySQL Server
- Postman

## Tecnologías Utilizadas

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Spring Data JPA / Hibernate
- MySQL
- Lombok
- Resilience4j

## Instalación y Configuración

1. Clonar el repositorio.

Abriendo una consola de windows (CMD)
```
git clone https://github.com/ChrisPerezV/donaton-ms--ev2.git
```

2. Creación de BD.

Para levantar las bases de datos de forma automática, asegúrese de tener Docker Desktop iniciado y ejecute.

Verificar funcionamiento de docker en el equipo.
Dirigirse a la ruta en la cual se clonó el proyecto.
```
cd donaton-ms--ev2
```
Dirigirse a carpeta de configuración de docker.
```
cd donaton-infra
```
Ejecutar comando.
```
docker-compose up -d
```
Se incializan contenedores docker con Mysql con bases de datos para el proyecto.

3. Compilación del Proyecto
Ejecute el siguiente comando en la raíz de cada microservicio.

Abriendo una consola de windows(CMD).
```
mvnw clean package
```
Para asegurar que la comunicación entre servicios funcione correctamente, inicie los componentes en este orden.

- API Gateway (Puerto 8080)

- MS Necesidades (Puerto 8082)

- MS Logística (Puerto 8084)

- MS Donaciones (Puerto 8083)

4. Pruebas de Endpoints (Postman)
Todas las peticiones deben dirigirse al API Gateway (8080).

Crear un Centro de acopio.

POST
```
http://localhost:8080/api/v1/logistica/centros-acopio
```
JSON
```
{
    "nombre": "Gimnasio Las Palmas",
    "direccion": "Av. Los Alamos 1234",
    "idComuna": 5101,
    "capacidadMaximaKilos": 500
}
```
GET (Para obtener el ID del centro de acopio, se muestra de igual manera al realizar un POST)
```
http://localhost:8080/api/v1/logistica/centros-acopio
```

Crear una Necesidad.

POST
```
http://localhost:8080/api/v1/necesidades
```

JSON
```
{
    "idUsuarioCreador": "usr-12345",
    "tituloEmergencia": "Inundación en sector sur",
    "idComuna": 5101,
    "direccionEspecifica": "Calle los Patos 123",
    "items": [
        {
            "categoria": "AGUA",
            "descripcionItem": "Agua Purificada",
            "cantidadRequerida": 100,
            "unidadMedida": "LITROS",
            "prioridad": "ALTA_URGENCIA"
        },
        {
            "categoria": "ROPA",
            "descripcionItem": "Polera",
            "cantidadRequerida": 100,
            "unidadMedida": "UNIDADES",
            "prioridad": "MEDIA"
        }
    ]
}
```
GET (Para obtener el ID de la necesidad, se muestra de igual manera al realizar un POST)
```
http://localhost:8080/api/v1/necesidades
```

Donar al centro de acopio.

POST
```
http://localhost:8080/api/v1/donaciones
```
JSON
```
{
    "idDonante": "204894736",
    "tipoDonacion": "ESPECIE",
    "idCentroAcopio": "ID_DEL_CENTRO_DE_ACOPIO",
    "items": [
        {
            "categoria": "AGUA",
            "cantidad": 50,
            "descripcionItem": "Botellones de agua purificada",
            "unidadMedida": "LITROS"
        }
    ]
}
```
GET (Para obtener el ID de la donación, se muestra de igual manera al realizar un POST)
```
http://localhost:8080/api/v1/donaciones
```

Crear Despacho (Al crear el despacho en curso se resta del centro de acopio distribuido, pero aun no se entrega a la necesidad).

POST
```
http://localhost:8080/api/v1/logistica/despachos
```

JSON
```
{
    "idCentroOrigen": "ID_DEL_CENTRO_DE_ACOPIO",
    "idNecesidadDestino": "ID_DE_LA_NECESIDAD_DESTINO",
    "patenteVehiculo": "AB-CD-12",
    "items": [
        {
            "categoria": "AGUA",
            "cantidad": 50
        }
    ]
}
```

GET (Para obtener el ID del despacho, se muestra de igual manera al realizar un POST)
```
http://localhost:8080/api/v1/logistica/despachos
```

Modificar despacho para entrega (Al cambiar estado de entrega a "ENTREGADO", se suma la cantidad al centro de acopio del respectivo item / ingresar ID del despacho a la ruta de PUT).

PUT
```
http://localhost:8080/api/v1/logistica/despachos/ID_DEL_DESPACHO/estado?nuevoEstado=ENTREGADO
```