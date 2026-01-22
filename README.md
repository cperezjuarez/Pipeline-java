# Gestor de series y plataformas

Aplicación que gestiona series y plataformas con backend en Spring Boot y frontend en Angular.

## Requisitos

- Java 21
- Maven
- Node.js 18+ (para el frontend)
- npm

## Estructura de Carpetas

### Backend (Spring Boot)
- `back-end/gestor-series-plataformes/` - Aplicación Spring Boot
  - `src/main/java/ifc33b/dwesc/gestor_series_plataformes/` - Código fuente Java
    - `controller` - Controladores
    - `dto` - Modelos de transferencia entre front y back
    - `model` - Modelos Java
    - `repository` - JPA
    - `service` - Lógica de negocio
    - `exception` - Control de excepciones
  - `src/main/resources/` - Recursos de la aplicación
  - `src/test/` - Tests unitarios
  - `pom.xml` - Dependencias Maven

### Frontend (Angular)
- `front-end/gestor-series-plataformes/` - Aplicación Angular
  - `src/app/` - Código Angular
    - `components/` - Componentes reutilizables
    - `models/` - Modelos TypeScript
    - `services/` - Servicios (comunicación con backend)
  - `src/` - Assets y configuración
  - `package.json` - Dependencias npm

## Instalación

### Backend

1. Acceder a la carpeta del backend:
```bash
cd back-end/gestor-series-plataformes
```

2. Compilar y ejecutar:
```bash
mvn spring-boot:run
```

El servidor estará disponible en `http://localhost:8080`

### Frontend

1. Acceder a la carpeta del frontend:
```bash
cd front-end/gestor-series-plataformes
```

2. Instalar dependencias:
```bash
npm install
```

3. Ejecutar servidor de desarrollo:
```bash
npm start
```

La aplicación estará disponible en `http://localhost:4200`

## Desarrollo

### Backend

El backend está construido con:
- **Spring Boot 3.x** - Framework web
- **Spring Data JPA** - Acceso a datos
- **Maven** - Gestor de dependencias

### Frontend

El frontend está construido con:
- **Angular 18+** - Framework de desarrollo
- **TypeScript** - Lenguaje de programación
- **TailwindCSS** - Estilos
- **npm** - Gestor de dependencias

## Endpoints API

La API está disponible en `http://localhost:8080/api`

### Tabla Resumen de Endpoints

| Método | Ruta | Descripción | Status |
|--------|------|-------------|--------|
| GET | `/api/plataformes` | Obtener todas las plataformas | 200 |
| GET | `/api/series/plataforma/{id}` | Obtener las series de una plataforma | 200 |
| POST | `/api/series` | Añadir una nueva serie a una plataforma | 201 |

### Endpoints detallados

#### 1. Obtener todas las plataformas

**GET** `/api/plataformes`

Devuelve la lista de plataformas.

**Response (200 OK):**
```json
[
  { "id": 1, "nom": "Netflix" },
  { "id": 2, "nom": "Disney+" }
]
```

---

#### 2. Obtener las series de una plataforma

**GET** `/api/series/plataforma/{id}`

Obtiene todas las series asociadas a la plataforma con el `id` proporcionado.

**Response (200 OK):**
```json
[
  { "id": 1, "titol": "Stranger Things", "genere": "Drama", "plataformaId": 1 },
  { "id": 2, "titol": "Dark", "genere": "Ciencia ficción", "plataformaId": 1 }
]
```

---

#### 3. Añadir una nueva serie a una plataforma

**POST** `/api/series`

Crea una nueva serie y la asocia a la plataforma indicada.

**Headers:**
```
Content-Type: application/json
```

**Request Body:** (ejemplo)
```json
{
  "titol": "Mi Serie",
  "genere": "Comedia",
  "plataformaId": 1
}
```

**Parámetros requeridos:**
- `titol` (string, obligatorio): Nombre de la serie, entre 3 y 25 caracteres
- `genere` (string, obligatorio): Genero de la serie, entre 3 y 25 caracteres
- `plataformaId` (int, obligatorio): ID de la plataforma a la que pertenece

**Response (201 Created):**
```json
{
  "id": 3,
  "titol": "Mi Serie",
  "genere": "Comedia",
  "plataformaId": 1
}
```

---

### Ejemplos con cURL

Obtener plataformas:
```bash
curl -X GET "http://localhost:8080/api/plataformes"
```

Obtener series de la plataforma 1:
```bash
curl -X GET "http://localhost:8080/api/series/plataforma/1"
```

Crear una serie:
```bash
curl -v -X POST "http://localhost:8080/api/series" -H "Content-Type: application/json" --data-raw '{"titol":"Mi Serie","genere":"Comedia","plataformaId":1}'
```

## Estructura del Proyecto

```
gestor-series-plataformes/
├── README.md
├── back-end/
│   └── gestor-series-plataformes/
│       ├── mvnw
│       ├── mvnw.cmd
│       ├── pom.xml
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/ifc33b/dwesc/gestor-series-plataformes/
│       │   │   │   ├── GestorSeriesPlataformesApplication.java
│       │   │   │   ├── controller/
│       │   │   │   │   └── GestorController.java
│       │   │   │   ├── dto/
│       │   │   │   │   ├── PlataformaResponse.java
│       │   │   │   │   ├── SerieRequest.java
│       │   │   │   │   └── SerieResponse.java
│       │   │   │   ├── model/
│       │   │   │   │   ├── Plataforma.java
│       │   │   │   │   └── Serie.java
│       │   │   │   ├── repository/
│       │   │   │   │   ├──   PlataformaRepository.java
│       │   │   │   │   └──   SerieRepository.java
│       │   │   │   ├── service/
│       │   │   │   │   └──  GestorService.java
│       │   │   │   └── exception/
│       │   │   │       ├── GlobalExceptionHandler.java
│       │   │   │       └── PlataformaNotFoundException.java
│       │   │   └── resources/
│       │   │       ├── sql
│       │   │       │   └── data.sql
│       │   │       └── application.properties
│       │   └── test/
│       │       └── java/ifc33b/dwesc/ranking/
│       │           └── GestorSeriesPlataformesApplicationTests.java
│       └── target/ (generado por Maven)
└── front-end/
    └── gestor-series-plataformes/
        ├── angular.json
        ├── package.json
        ├── tsconfig.json
        ├── tsconfig.app.json
        ├── tsconfig.spec.json
        ├── README.md
        ├── public/
        ├── src/
        │   ├── index.html
        │   ├── main.ts
        │   ├── styles.scss
        │   └── app/
        │       ├── app.config.ts
        │       ├── app.html
        │       ├── app.routes.ts
        │       ├── app.scss
        │       ├── app.ts
        │       ├── app.spec.ts
        │       ├── components/
        │       │   ├── formulari-series/
        │       │   │   ├── formulari-series.html
        │       │   │   ├── formulari-series.scss
        │       │   │   ├── formulari-series.ts
        │       │   │   └── formulari-series.spec.ts
        │       │   ├── llista-plataformes/
        │       │   │   ├── llista-plataformes.html
        │       │   │   ├── llista-plataformes.scss
        │       │   │   ├── llista-plataformes.ts
        │       │   │   └── llista-plataformes.spec.ts
        │       │   └── llista-series/
        │       │       ├── llista-series.html
        │       │       ├── llista-series.scss
        │       │       ├── llista-series.ts
        │       │       └── llista-series.spec.ts
        │       ├── models/
        │       │   ├── index.ts
        │       │   ├── serie.model.ts
        │       │   ├── serie.model.spec.ts
        │       │   ├── plataforma.model.ts
        │       │   └── plataforma.model.spec.ts
        │       └── services/
        │           ├── gestor.service.ts
        │           └── gestor.service.spec.ts
```