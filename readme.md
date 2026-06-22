# Donatón - Sistema de Microservicios

Este repositorio contiene la arquitectura de microservicios para la plataforma Donatón. El sistema está diseñado utilizando Spring Boot 3.3.6, Java 21, Spring Cloud Gateway, Resilience4j (Circuit Breaker) y MySQL como motor de base de datos relacional.

## Arquitectura del Sistema
La solución está dividida en los siguientes componentes:

- api-gateway (Puerto 8080): Punto de entrada único del sistema. Se encarga del enrutamiento dinámico, configuración de CORS y resiliencia.

- ms-usuarios (Puerto 8085): Gestión de usuarios, autenticación y emisión de credenciales.

- ms-necesidades (Puerto 8082): Registro y administración de solicitudes de ayuda o necesidades.

- ms-donaciones (Puerto 8083): Gestión de aportes y asignación a necesidades específicas.

- ms-logistica (Puerto 8084): Control de despachos, inventario y estados de envío.

- mysql-db (Puerto 3306): Instancia compartida de base de datos con aislamiento por esquemas lógicos.

# Ejecución en Entorno Local
Para modificar, visualizar y probar el código de manera local antes de subirlo a la nube, se utiliza Docker Compose.

### Requisitos Previos
- Docker Desktop instalado y corriendo.

- Java Development Kit (JDK) 21.

- Maven

## Paso 1: Compilación de los Microservicios
Debido a que la base de datos local inicia vacía, la fase de compilación debe omitir temporalmente las pruebas unitarias de contexto de persistencia para evitar fallos de conexión. Ejecuta el empaquetado en la raíz de cada servicio:
```
En Windows (CMD o PowerShell)
cd donaton
cd ms-usuarios && mvnw.cmd clean package -DskipTests && cd ..
cd ms-necesidades && mvnw.cmd clean package -DskipTests && cd ..
cd ms-logistica && mvnw.cmd clean package -DskipTests && cd ..
cd ms-donaciones && mvnw.cmd clean package -DskipTests && cd ..
cd api-gateway && mvnw.cmd clean package -DskipTests && cd ..
```

## Paso 2: Levantar la Infraestructura Local
Una vez creados los archivos .jar dentro de las carpetas target/, ejecuta el archivo de orquestación principal en la raiz del proyecto:
```
docker compose up -d --build
```
Esto compilará las imágenes locales, inicializará los esquemas de bases de datos mediante los scripts del directorio ./donaton-infra/mysql-init y encenderá todo el ecosistema.

## Paso 3: Pruebas de Endpoints
Todas las peticiones deben dirigirse al API Gateway en el puerto 8080.

Inicio de Sesión

(La respuesta a la petición entregará un token, este es necesario agregarlo a cada consulta que se realice posteriormente en la seccion Authorization/Bearer Token de Postman): 

POST
``` 
http://localhost:8080/api/v1/usuarios/login
```
JSON para admin
```
{
    "email": "admin@donaton.com",
    "password": "password"
}
```
JSON para donante
```
{
    "email": "juan@correo.com",
    "password": "password"
}
```
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
# Despliegue en Producción (AWS)
El despliegue productivo utiliza una infraestructura de alta seguridad basada en el principio de mínimo privilegio dentro de Amazon Web Services (AWS).

Flujo de Integración y Despliegue Continuo (CI/CD)
Push a GitHub: Los cambios confirmados en la rama principal (main) disparan el pipeline automatizado en GitHub Actions.

Construcción y Registro: El pipeline compila el código, empaqueta las imágenes Docker y las publica en un repositorio privado de AWS ECR.

A través de llaves encriptadas seguras, el pipeline se conecta a la infraestructura y actualiza las imágenes correspondientes.

Acceso Seguro y Pruebas en la Nube
El servidor que aloja los contenedores productivos se encuentra dentro de una VPC con subredes privadas, lo que significa que no tiene una dirección IP pública directa a Internet. Para interactuar con las APIs de producción desde herramientas locales como Postman, se debe utilizar el Bastion Host o EC2 Pública ubicado en la subred pública como puente.

### Arquitectura AWS
La arquitectura realizada para prueba de CI/CD es una con una zona de disponibilidad, en la cual posee:
#### Subred pública
- EC2 con IP Pública asiganda automaticamente por AWS cada vez que se reinicia
- NAT Gateway, disponible para habilitar la salida de la subred privada a traves de la subred pública con IP Elástica
- Grupo de seguridad con permisos para navegar con salida hacia internet y hacer consultas al puerto 8080 y SSH internamente a la subred privada
- Tabla de enrutamiento para salida de internet y consultas a subred privada

#### Subred privada
- EC2 privada donde se alojan los microservicios anteriormente nombrados junto a sus bases de datos separadas por microservicios
- Grupo de seguridad con permisos para salida a internet, consultas hacia el puerto 8080 y SSH
- Tabla de enrutamiento para la salida de internet a traves de NAT Gateway

### Secretos Github

Se configuraron secretos en Github para mantener la seguridad del despliegue a AWS, entre ellos:
- AWS_ACCESS_KEY_ID (ID Llave sesion AWS)
- AWS_ACCOUNT_ID (ID de cuenta AWS)
- AWS_REGION (Región de zona de disponibilidad)
- AWS_SECRET_ACCESS_KEY (Llave secreta de sesion AWS)
- AWS_SESSION_TOKEN (Token de inicio de sesion AWS)
- BASTION_HOST (IP Pública de EC2 subred pública)
- EC2_SSH_KEY (Llave.pem generada al crear EC2)
- EC2_USERNAME (Usuario configurado en EC2)
- PRIVATE_EC2_IP (IP Privada de EC2 subred privada)

### Establecer el Túnel SSH
Ejecuta el siguiente comando en tu terminal local para abrir un canal seguro de reenvío de puertos a través del Bastion Host:

```
ssh -i "tu-llave.pem" -L 8080:IP_PRIVADA_EC2_MICROSERVICIOS:8080 usuario@IP_PUBLICA_BASTION
```
Posterior a levantar túnel SSH se pueden realizar pruebas con POSTMAN dirigidas al localhost