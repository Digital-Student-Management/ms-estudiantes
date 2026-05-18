# 🎓 Microservicio de Gestión de Estudiantes y Matrículas (ms-estudiantes)

Este repositorio contiene el código fuente del **Microservicio de Gestión de Estudiantes, Matrículas e Historial Académico**, módulo central del Sistema Integral de Gestión Estudiantil Digital para el **Colegio Bernardo O'Higgins de Coquimbo**.

Este componente administra todo el ciclo de vida del alumnado dentro del establecimiento, desde el proceso formal de inscripción y matrícula anual, pasando por el registro conductual (anotaciones), hasta la consolidación de fichas de antecedentes médicos, sociofamiliares y de procedencia académica.

---

## 🚀 Descripción de la Arquitectura y Dominio

El `ms-estudiantes` opera bajo una arquitectura distribuida desacoplada. Maneja la información académica sin dependencias compartidas ni accesos directos a bases de datos de terceros. La sincronización e integridad del sistema se logra mediante **Referencias Suaves (Soft References)** relacionales apuntando a los identificadores del núcleo de identidades (`ms-usuarios`) y del futuro módulo académico (`ms-cursos`).

### 🧬 Reestructuración y Organización del Dominio
Atendiendo a los estándares de desarrollo limpio de la industria, las clases del modelo de datos de este servicio se encuentran estrictamente organizadas dentro de la subcarpeta jerárquica de entidades `models/entities/`:

* **`Matricula`:** Controla el estado registral del alumno en un año lectivo (VIGENTE, RETIRADO, SUSPENDIDO) y lo enlaza lógicamente a un curso.
* **`Anotacion`:** Administra las bitácoras conductuales, clasificando las observaciones en registros de índole POSITIVA o NEGATIVA, amarrados al ID del docente evaluador.
* **`Ficha de Antecedentes`:** Estructura de persistencia unificada mediante relaciones JPA que consolida la `HojaVida`, `AntecedentesMedicos` (alergias, medicamentos), `AntecedentesAcademicos` (promedios, procedencia) y `AntecedentesApoderado`.

---

## 🛠️ Stack Tecnológico

* **Lenguaje de Programación:** Java 21 (LTS)
* **Framework Core:** Spring Boot 4.0.6
* **Gestor de Dependencias:** Maven
* **Base de Datos Relacional:** MySQL 8.x
* **Mapeo Objeto-Relacional (ORM):** Spring Data JPA / Hibernate 7.x
* **Documentación Interactiva:** SpringDoc OpenAPI 3.0.2 (Swagger UI)
* **Utilidades:** Lombok (Getters, Setters, Data annotations globales)
* **Validación de Datos:** Jakarta Validation

---

## 📁 Estructura del Proyecto

```text
ms-estudiantes/
├── peticiones.rest                          ← Archivo de pruebas rápidas (REST Client)
├── pom.xml                                  ← Dependencias del proyecto Maven
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── ms_estudiantes/
        │               ├── MsEstudiantesApplication.java   ← Clase principal de arranque
        │               ├── config/                       ← Configuraciones globales de red
        │               │   ├── RestTemplateConfig.java
        │               │   └── WebConfig.java            ← Políticas de CORS para React (Puerto 3000)
        │               ├── controllers/                  ← Controladores de endpoints REST
        │               │   ├── MatriculaController.java
        │               │   ├── AnotacionController.java
        │               │   └── AntecedentesController.java
        │               ├── dtos/                         ← Data Transfer Objects (Request/Response)
        │               │   ├── MatriculaRequestDTO.java
        │               │   ├── MatriculaResponseDTO.java
        │               │   ├── AnotacionRequestDTO.java
        │               │   ├── AnotacionResponseDTO.java
        │               │   └── FichaAntecedentesDTO.java
        │               ├── models/
        │               │   └── entities/                 ← ¡Ubicación refactorizada de entidades!
        │               │       ├── Matricula.java
        │               │       ├── Anotacion.java
        │               │       ├── HojaVida.java
        │               │       ├── AntecedentesMedicos.java
        │               │       ├── AntecedentesApoderado.java
        │               │       └── AntecedentesAcademicos.java
        │               ├── repositories/                 ← Capa de abstracción de datos JPA
        │               │   ├── MatriculaRepository.java
        │               │   ├── AnotacionRepository.java
        │               │   └── ...
        │               └── services/                     ← Capa productiva de lógica escolar
        │                   ├── MatriculaService.java
        │                   ├── AnotacionService.java
        │                   └── AntecedentesService.java
        └── resources/
            └── application.properties                ← Propiedades de puertos y conexión
```

---

## ⚙️ Instalación y Despliegue Local

### 1. Clonar y Configurar Entorno
Asegúrate de contar con el puerto **8082** libre en tu máquina local.

```bash
git clone [https://github.com/TuOrganizacion/ms-estudiantes.git](https://github.com/TuOrganizacion/ms-estudiantes.git)
cd ms-estudiantes
```

### 2. Variables de Lanzamiento de Entorno (`.vscode/launch.json`)
Crea o edita tu archivo de lanzamiento local en tu entorno de Visual Studio Code para inyectar dinámicamente las variables de conexión a la base de datos MySQL sin comprometer la seguridad del repositorio:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Arrancar MS Estudiantes",
            "request": "launch",
            "mainClass": "com.example.ms_estudiantes.MsEstudiantesApplication",
            "projectName": "ms-estudiantes",
            "env": {
                "DB_URL": "jdbc:mysql://localhost:3306/ms_estudiantes_db?createDatabaseIfNotExist=true&serverTimezone=UTC",
                "DB_USER": "root",
                "DB_PASSWORD": "tu_contraseña_local"
            }
        }
    ]
}
```

### 3. Iniciar el Servidor
Ejecuta la aplicación mediante las herramientas nativas de VS Code o ingresando el siguiente comando en la terminal:
```bash
./mvnw spring-boot:run
```

---

## 🔌 Documentación y Pruebas en Swagger UI

Al levantar el microservicio, el catálogo completo de rutas REST estructuradas queda disponible de forma interactiva en la siguiente URL:

`http://localhost:8082/swagger-ui.html`

### Resumen de Rutas y Controladores Expuestos

| Método HTTP | Ruta Base del Endpoint | Responsabilidad Operativa |
|---|---|---|
| `POST` | `/api/matriculas` | Inscribe una nueva matrícula escolar para un alumno. |
| `GET` | `/api/matriculas` | Lista todos los registros de matrícula del establecimiento. |
| `GET` | `/api/matriculas/{id}` | Obtiene los detalles de una matrícula por su ID único. |
| `PUT` | `/api/matriculas/{id}` | Actualiza el estado de la matrícula (VIGENTE, RETIRADO, etc.). |
| `POST` | `/api/anotaciones` | Registra una observación conductual (positiva o negativa). |
| `GET` | `/api/anotaciones/estudiante/{id}` | Filtra y recupera el historial de anotaciones de un alumno. |
| `POST` | `/api/antecedentes` | Almacena y unifica las fichas médicas y académicas de un alumno. |
| `GET` | `/api/antecedentes/estudiante/{id}` | Obtiene la ficha de vida completa y consolidada del estudiante. |

---

## 🧪 Pruebas con la Extensión REST Client

Para agilizar los ciclos de testing en la capa del controlador sin requerir de software adicional como Postman, se incorpora el archivo `peticiones.rest` en la raíz del proyecto.

1. Instala la extensión **REST Client** de *Huachao Mao* en VS Code.
2. Abre el archivo `peticiones.rest`.
3. Presiona el botón flotante **`Send Request`** ubicado encima de cada ruta HTTP.
4. Analiza la respuesta estructurada del servidor en la pestaña derecha dividida.