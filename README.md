# User Microservice

## Descripci�n
Este es un microservicio para la creaci�n y consulta de usuarios.

## Requisitos
- Java 8
- Gradle hasta 7.4
- SpringBoot 2.5.14

## Construcci�n y Ejecuci�n del Proyecto

### Construcci�n
Para construir el proyecto, correr el siguiente comando en la ra�z del proyecto:

```bash
./gradlew build
```

### Ejecuci�n
Para ejecutar el proyecto, correr el siguiente comando en la ra�z del proyecto:

```bash
./gradlew bootRun
```

## Endpoints

### /sign-up
Endpoint para la creaci�n de un usuario.

#### M�todo: POST
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
Endpoint para consultar un usuario utilizando el token generado en el endpoint de creaci�n.

#### M�todo: POST
#### URL: /api/users/login
#### Encabezado de la solicitud:
```
{
    "Authorization": "Bearer {token}"
}
```
## Tests Unitarios

Para ejecutar los tests unitarios correr el siguiente comando desde la ra�z del proyecto:
```
./gradlew test
```
Para ejecutar los tests y generar el informe de cobertura correr el siguiente comando desde la ra�z del proyecto:
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

En base al enunciado del ejercicio, se realiz� un Microservicio para la creaci�n y consulta de usuarios, se utiliz� java 8, gradle, Spring boot 2.5.14. Adem�s se hicieron validaciones mediante expresiones regulares, manejo de excepciones, generaci�n de tokens JWT, persistencia en base de datos en memoria H2 y pruebas unitarias.
El enunciado del ejercicio se encuentra en la ruta:
```
/src/main/resouces/static
```