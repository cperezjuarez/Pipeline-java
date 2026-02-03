# Gestor de Series y Plataformas

Aplicación Spring Boot para gestionar series de streaming y sus plataformas, construida con Java 21, Maven, PostgreSQL y Docker.

## 📋 Introducción y Estructura

### Arquitectura del Proyecto

```
gestor-series-plataformes/
├── src/
│   ├── main/
│   │   ├── java/ifc33b/dwesc/gestor_series_plataformes/
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── service/            # Lógica de negocio
│   │   │   ├── repository/         # Acceso a datos (JPA)
│   │   │   ├── model/              # Entidades JPA
│   │   │   ├── dto/                # Objetos de transferencia
│   │   │   └── GestorSeriesPlataformesApplication.java
│   │   └── resources/
│   │       ├── application.properties           # Configuración principal
│   │       ├── application-test.properties     # Configuración tests
│   │       ├── application-prod.properties     # Configuración producción
│   │       └── data-postgresql.sql              # Datos iniciales
│   └── test/                         # Tests unitarios e integración
├── docker-compose.yml                # Configuración Docker
├── checkstyle.xml                   # Reglas de estilo
└── pom.xml                         # Dependencias Maven
```

### Tecnologías Utilizadas

- **Backend:** Spring Boot 3.3.5, Java 21
- **Base de Datos:** PostgreSQL 15
- **Contenerización:** Docker & Docker Compose
- **Tests:** JUnit 5, Mockito, TestContainers
- **CI/CD:** GitHub Actions
- **Calidad:** Checkstyle, Maven Surefire

---

## 🚀 Puesta en Marcha

### Prerrequisitos

- Docker y Docker Compose
- Java 21 JDK
- Maven 3.8+

### 1. Iniciar la Aplicación con Docker

```bash
# Clonar el repositorio
git clone https://github.com/cperezjuarez/Pipeline-java.git
cd Pipeline-java/gestor-series-plataformes

# Compilar el proyecto
mvn clean package -DskipTests # Compilamos el proyecto sin ejecutar los tests porque necesitamos que PostgreSQL esté levantado y eso lo hacemos en el docker compose

# Iniciar PostgreSQL y la aplicación
docker compose up -d

# Verificar que los contenedores estén corriendo
docker ps
```

### 2. Cargar Datos de Prueba (Fixtures)

Los datos se cargan automáticamente al iniciar PostgreSQL gracias al volumen configurado en `docker-compose.yml`:

```yaml
volumes:
  - ./src/main/resources/data-postgresql.sql:/docker-entrypoint-initdb.d/init.sql
```

**Datos incluidos:**
- 10 plataformas (Netflix, Disney+, HBO, etc.)
- 25+ series distribuidas entre las plataformas

### 3. Verificar la Aplicación

```bash
# Verificar logs de la aplicación
docker logs gestor-series-app

# Verificar conexión a la base de datos
docker logs gestor-series-db
```

---

## 🧪 Ejecución de Tests

### Tests Locales con PostgreSQL

**Nota:** Los tests se ejecutan automáticamente en GitHub Actions, pero puedes ejecutarlos localmente iniciando el contendor de docker con Tomcat y PostgreSQL:

```bash
docker compose up -d
```

---

## 🔍 Verificación del Funcionamiento

### Endpoints Principales

La aplicación expone los siguientes endpoints en `http://localhost:8080/api`:

#### 1. Obtener Todas las Plataformas
```bash
curl -X GET http://localhost:8080/api/plataformes
```

**Respuesta esperada:**
```json
[
  {"id": 1, "nom": "Netflix"},
  {"id": 2, "nom": "Disney+"},
  {"id": 3, "nom": "HBO"},
  ...
]
```

#### 2. Obtener Series por Plataforma
```bash
curl -X GET http://localhost:8080/api/series/plataforma/1
```

**Respuesta esperada:**
```json
[
  {
    "id": 1,
    "titol": "Stranger Things",
    "genere": "Ciencia ficción",
    "plataforma_id": 1
  },
  {
    "id": 2,
    "titol": "The Crown",
    "genere": "Drama",
    "plataforma_id": 1
  }
]
```

#### 3. Crear Nueva Serie
```bash
curl -X POST http://localhost:8080/api/series -H "Content-Type: application/json" -d '{"titol": "Nueva Serie", "genere": "Drama", "plataformaId": 1}'
```

### Colección Postman

Se incluye una colección de Postman en `postman/gestor-series-plataformes.json` con todos los endpoints:

```bash
# Importar colección en Postman
1. Abrir Postman
2. File > Import
3. Seleccionar el archivo gestor-series-plataformes.json
4. Ejecutar la los endpoints
```

---

## 🧪 Suite de Tests

### Tests Unitarios

**GestorServiceTest.java**
- `getPlataformes_ShouldReturnAllPlatforms`: Verifica obtención de todas las plataformas
- `getSeries_ShouldReturnSeriesForPlatform`: Verifica filtrado de series por plataforma
- `createSerie_ShouldCreateNewSeries`: Verifica creación de nuevas series
- `createSerie_ShouldReturn404WhenPlatformDoesNotExist`: Manejo de plataforma inexistente

**PlataformaRepositoryTest.java**
- `findAll_ShouldReturnAllPlatforms`: Prueba de repositorio findAll
- `findById_ShouldReturnPlatformWhenExists`: Prueba de búsqueda por ID
- `save_ShouldCreateNewPlatform`: Prueba de guardado
- `deleteById_ShouldDeletePlatform`: Prueba de eliminación

**SerieRepositoryTest.java**
- `findAll_ShouldReturnAllSeries`: Prueba de obtención de todas las series
- `findById_ShouldReturnSeriesWhenExists`: Prueba de búsqueda por ID
- `getSeriesInPlataforma_ShouldReturnSeriesForSpecificPlatform`: Prueba de filtrado
- `save_ShouldCreateNewSeries`: Prueba de creación
- `deleteById_ShouldDeleteSeries`: Prueba de eliminación

### Tests de Integración

**IntegrationTest.java**
- `contextLoads`: Verifica carga del contexto Spring
- `getPlataformas_ShouldReturnAllPlatforms`: Test completo del endpoint GET /plataformes
- `getPlataformas_ShouldReturnEmptyListWhenNoPlatformsExist`: Manejo de lista vacía
- `getSeriesByPlataforma_ShouldReturnSeriesForPlatform`: Test completo del endpoint GET /series/plataforma/{id}
- `getSeriesByPlataforma_ShouldReturn404WhenPlatformDoesNotExist`: Manejo de 404
- `createSerie_ShouldCreateNewSeries`: Test completo del endpoint POST /series
- `createSerie_ShouldReturn400WhenValidationFails`: Manejo de validación
- `completeFlow_ShouldWorkEndToEnd`: Flujo completo E2E

**GestorSeriesPlataformesApplicationTests.java**
- `contextLoads`: Verificación básica del contexto

### Estadísticas de Tests

- **Total de tests:** 42
- **Tests unitarios:** 34
- **Tests de integración:** 8
- **Cobertura esperada:** >80%

---

## 🔄 GitHub Actions Workflow

### Archivo: `.github/workflows/ci.yml`

#### Descripción del Pipeline

**Trigger:**
- Push a la rama `main`
- Pull Requests a `main`

**Jobs:**

##### 1. test-and-build
**Runner:** `ubuntu-latest`

**Servicios:**
- **PostgreSQL 15** con configuración:
  - Base de datos: `gestor_series_test`
  - Usuario: `postgres`
  - Password: `root`
  - Health checks para asegurar disponibilidad

**Steps:**
1. **Checkout code:** Descarga del código fuente
2. **Set up JDK 21:** Configuración de Java 21 con Temurin
3. **Cache Maven dependencies:** Caché de dependencias para acelerar builds
4. **Run Checkstyle:** Verificación de estilo de código
5. **Run unit tests:** Ejecución de tests con PostgreSQL (`-Dspring.profiles.active=test`)
6. **Build application:** Compilación y empaquetado (`mvn clean package -DskipTests`)
7. **Build and run Docker Compose:** Construcción y ejecución de contenedores
8. **Upload test results:** Subida de resultados de tests como artefactos

##### 2. deploy
**Runner:** `ubuntu-latest`
**Condición:** Solo en commits a `main`

**Steps:**
1. **Checkout code:** Descarga del código
2. **Deploy:** Ejemplo de dónde habría que poner el deploy en un caso real

---

**Autor:** Cristian Pérez Juárez 
**Versión:** 1.0.0  
**Última actualización:** Febrero 2026