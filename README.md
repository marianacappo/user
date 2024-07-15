# User Microservice

## Descripción
Este es un microservicio para la creación y consulta de usuarios.

## Requisitos
- Java 8
- Gradle hasta 7.4
- SpringBoot 2.5.14

## Construcción y Ejecución del Proyecto

### Construcción
Para construir el proyecto, correr el siguiente comando en la raíz del proyecto:

```bash
./gradlew build
```

### Ejecución
Para ejecutar el proyecto, correr el siguiente comando en la raíz del proyecto:

```bash
./gradlew bootRun
```

## Endpoints

### /sign-up
Endpoint para la creación de un usuario.

#### Método: POST
#### URL: /api/users/sign-up
#### Cuerpo (json): 
```
{
    "name": "Juan Perez",
    "email": "juan.perez@email.com",
    "password": "Passw01d",
    "phones": [
        {
            "number": 123456789,
            "citycode": 1,
            "countrycode": "57"
        }
    ]
}
```
### /login
Endpoint para consultar un usuario utilizando el token generado en el endpoint de creación.

#### Método: POST
#### URL: /api/users/login
#### Encabezado de la solicitud:
```
{
    "Authorization": "Bearer {token}"
}
```
## Tests Unitarios

Para ejecutar los tests unitarios correr el siguiente comando desde la raíz del proyecto:
```
./gradlew test
```
Para ejecutar los tests y generar el informe de cobertura correr el siguiente comando desde la raíz del proyecto:
```
./gradlew test jacocoTestReport
```
### /sign-up

### Diagramas UML

#### Diagrama de Componentes y Diagrama de Secuencia

Se pueden encontrar en el proyecto en la ruta:
```
/src/main/resouces/static
```
### Resumen

En base al enunciado del ejercicio, se realizó un Microservicio para la creación y consulta de usuarios, se utilizó java 8, gradle, Spring boot 2.5.14. Además se hicieron validaciones mediante expresiones regulares, manejo de excepciones, generación de tokens JWT, persistencia en base de datos en memoria H2 y pruebas unitarias.
El enunciado del ejercicio se encuentra en la ruta:
```
/src/main/resouces/static
```