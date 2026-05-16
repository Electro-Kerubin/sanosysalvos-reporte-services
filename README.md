# sanosysalvos-reporte-services

Microservicio de reportes para Sanos y Salvos.

Este servicio no debe exponerse directamente al frontend. El acceso externo debe pasar por el API Gateway central, que aplica autenticación, autorización, trazabilidad y ruteo.

## Ruta base

- API expuesta para el gateway: `/api/reportes/**`

## Contrato con el Gateway

El gateway debe publicar este microservicio bajo `/api/reportes/**` y centralizar autenticación, autorización, trazabilidad y ruteo.

El servicio mantiene esa base configurable mediante `API_PREFIX`, pero el valor por defecto sigue siendo `/api/reportes`.

Ejemplo de ruteo esperado en el gateway:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: reporte-service
          uri: http://reporte-service:${REPORTE_SERVICE_PORT:8082}
          predicates:
            - Path=/api/reportes/**
```

## Configuración

La aplicación se configura por variables de entorno:

- `SERVER_PORT`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JPA_DDL_AUTO`
- `JPA_SHOW_SQL`
- `API_PREFIX`

## Ejecución local

En Windows puedes usar `mvnw.cmd` para descargar y ejecutar Maven automáticamente si no tienes Maven instalado globalmente.
