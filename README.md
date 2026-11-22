# ms-barriodigital-catalog

Microservicio CRUD de **tipos de trámite** (nombre, requisitos, plazo estimado) y sus **cupos disponibles**.
No se conecta con el frontend: lo consume `ms-barriodigital-requests` al admitir un trámite.

| Dato | Valor |
|------|-------|
| Puerto | `8085` |
| Stack | Spring Boot 4.1.2 · Java 17 · Maven · MySQL |
| Base de datos | `barriodigital_catalog` (se crea sola) |

## Cómo levantar (local)

```powershell
# 1. Infraestructura (MySQL en el puerto 3307), desde barriodigital-infra/local
docker compose up -d

# 2. Este microservicio
.\mvnw spring-boot:run "-Dmaven.test.skip=true"
```

Al arrancar se cargan los tipos iniciales desde `data.sql` (no se duplican al reiniciar).

| Variable | Por defecto |
|----------|-------------|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `3307` |
| `CATALOG_DB_NAME` | `barriodigital_catalog` |
| `DB_USERNAME` / `DB_PASSWORD` | `root` / `root` |

## Endpoints

Base: `/api/v1/catalog/procedures`

| Método | Ruta | Descripción | Respuestas |
|--------|------|-------------|------------|
| GET | `/` | Listar tipos de trámite | 200 |
| GET | `/{id}` | Obtener uno | 200 · 404 |
| POST | `/` | Crear | 201 · 400 · 409 (código duplicado) |
| PUT | `/{id}` | Actualizar (incluye reiniciar cupos con `cupoDisponible`) | 200 · 400 · 404 · 409 |
| POST | `/{tipo}/reserve` | Reservar un cupo | 200 · 404 · 409 (sin cupo) |

## Contrato para ms-requests

Cuando un funcionario cambia un trámite de `INGRESADO` a `ADMITIDO`, requests llama a:

```
POST /api/v1/catalog/procedures/{tipoTramite}/reserve
```

| Respuesta | Significado | Qué hace requests |
|-----------|-------------|-------------------|
| `200 {"codigo":"PODA_ARBOLES","reservado":true,"cupoRestante":4}` | Cupo descontado | Cambia el estado a `ADMITIDO` |
| `409 {"codigo":"PODA_ARBOLES","reservado":false,"cupoRestante":0}` | Sin cupo o tipo inactivo | Rechaza la admisión |
| `404 {"error":"No existe el tipo de trámite ..."}` | Código desconocido | Rechaza la admisión |

## Tipos iniciales

Mismos códigos que envía el frontend en `tipoTramite`:

| Código | Nombre | Cupos |
|--------|--------|-------|
| `CERTIFICADO_RESIDENCIA` | Certificado de residencia | 20 |
| `LUMINARIA_APAGADA` | Luminaria apagada | 10 |
| `HOYO_CALLE` | Hoyo en la calle | 8 |
| `PODA_ARBOLES` | Poda de arboles | 5 |
| `RETIRO_CACHUREOS` | Retiro de cachureos | 3 |

## Decisiones de diseño

- **Descuento atómico:** el cupo se descuenta con un solo `UPDATE ... WHERE cupo_disponible > 0`. MySQL bloquea la fila mientras tanto, así dos admisiones simultáneas no pueden tomar el mismo último cupo.
- **POST para reservar:** reservar modifica datos, por eso no es un GET (un reintento automático de un GET podría descontar cupos de más).
- **Reinicio de cupos manual:** se hace con `PUT` enviando `cupoDisponible`.

## Cómo probar

```powershell
curl.exe -s http://localhost:8085/api/v1/catalog/procedures
curl.exe -s -X POST http://localhost:8085/api/v1/catalog/procedures/RETIRO_CACHUREOS/reserve
```
