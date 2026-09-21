# JournalistNote

## Objetivo

Aplicación móvil académica para que un periodista pueda administrar casos criminales y las entrevistas asociadas a dichos casos. La aplicación funciona completamente de manera local, sin servicios web ni APIs externas.

## Tecnologías utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| Kotlin | 2.2.10 | Lenguaje de programación |
| Jetpack Compose | BOM 2026.02.01 | Interfaz gráfica |
| Room | 2.7.2 | Persistencia local |
| SQLite | (interno de Room) | Motor de base de datos |
| Navigation Compose | 2.9.0 | Navegación entre pantallas |
| Material 3 | (BOM) | Diseño visual |
| KSP | 2.2.10-1.0.31 | Procesamiento de anotaciones Room |

## Arquitectura

Se utiliza una arquitectura **MVC (Model-View-Controller)** adaptada para Jetpack Compose.

```
+----------------------+
|        View          |
|   Jetpack Compose    |
|   (Pantallas UI)     |
+----------+-----------+
           |
           v
+----------------------+
|     Controller       |
|   Lógica de negocio  |
|   (CaseController)   |
|   (InterviewController)|
+----------+-----------+
           |
           v
+----------------------+
|        Model         |
| Room + SQLite + DAO  |
| (Case, Interview)    |
+----------------------+
```

### Flujo de datos

```
Usuario
   |
   v
Jetpack Compose (View)
   |
   v
Controller (CaseController / InterviewController)
   |
   v
DAO (CaseDao / InterviewDao)
   |
   v
Room (ORM)
   |
   v
SQLite (Base de datos local)
```

## Estructura de carpetas

```
app/src/main/java/com/example/journalistnote/
│
├── Model/                          # Capa de modelo y persistencia
│   ├── Case.kt                     # Entidad Case + enum CaseStatus
│   ├── Interview.kt                # Entidad Interview
│   ├── CaseDao.kt                  # Acceso a datos de casos
│   ├── InterviewDao.kt             # Acceso a datos de entrevistas
│   └── AppDatabase.kt              # Base de datos Room (Singleton)
│
├── View/                           # Capa de presentación
│   ├── screens/
│   │   ├── HomeScreen.kt           # Dashboard con resumen de casos
│   │   ├── CaseListScreen.kt       # Listado de casos con búsqueda
│   │   ├── CaseDetailScreen.kt     # Detalle de un caso + entrevistas
│   │   ├── CaseFormScreen.kt       # Crear/editar caso
│   │   └── InterviewFormScreen.kt  # Crear entrevista
│   ├── components/
│   │   ├── CaseCard.kt             # Tarjeta reutilizable de caso
│   │   └── SearchBar.kt            # Barra de búsqueda
│   └── navigation/
│       └── AppNavigation.kt        # Rutas de navegación
│
├── Controller/                     # Capa de lógica de negocio
│   ├── CaseController.kt           # Operaciones con casos
│   └── InterviewController.kt      # Operaciones con entrevistas
│
└── MainActivity.kt                 # Punto de entrada de la aplicación
```

## Descripción de clases

### Model (Modelo y Persistencia)

| Clase | Responsabilidad |
|---|---|
| `Case` | Entidad Room que representa un caso criminal. Contiene id, título, descripción, fecha, estado y conclusión. |
| `CaseStatus` | Enum que define los estados posibles: OPEN, IN_PROGRESS, CLOSED. |
| `Interview` | Entidad Room que representa una entrevista. Contiene id, caseId, entrevistado, fecha, notas y hallazgos. |
| `CaseDao` | Interfaz DAO con operaciones CRUD para casos, incluyendo búsqueda y conteo por estado. |
| `InterviewDao` | Interfaz DAO con operaciones CRUD para entrevistas, filtradas por caseId. |
| `AppDatabase` | Clase abstracta de Room que define la base de datos y proporciona acceso a los DAO. Utiliza patrón Singleton. |

### View (Presentación)

| Clase | Responsabilidad |
|---|---|
| `HomeScreen` | Dashboard que muestra resumen de casos: total, abiertos, en investigación, cerrados. |
| `CaseListScreen` | Listado de casos con barra de búsqueda en tiempo real. Permite crear nuevos casos. |
| `CaseDetailScreen` | Muestra información completa del caso y sus entrevistas. Permite editar, eliminar y agregar entrevistas. |
| `CaseFormScreen` | Formulario reutilizable para crear o editar casos. Validación de campos obligatorios. |
| `InterviewFormScreen` | Formulario para crear entrevistas asociadas a un caso. |
| `CaseCard` | Componente reutilizable que muestra resumen de un caso en una tarjeta. |
| `SearchBar` | Componente reutilizable de búsqueda con icono. |
| `AppNavigation` | Centraliza todas las rutas de navegación. |

### Controller (Lógica de Negocio)

| Clase | Responsabilidad |
|---|---|
| `CaseController` | Conecta la UI con CaseDao. Proporciona métodos para crear, leer, actualizar, eliminar y buscar casos. |
| `InterviewController` | Conecta la UI con InterviewDao. Proporciona métodos para crear, leer, actualizar y eliminar entrevistas. |

## Modelo de datos

### Diagrama de entidades

```
CASE (cases)
------------------
id          Long    (PK, autoincrement)
title       String
description String
date        String  (formato ISO-8601)
status      String  (enum: OPEN, IN_PROGRESS, CLOSED)
conclusion  String  (puede estar vacía)
        |
        | 1
        |
        | N
        v
INTERVIEW (interviews)
------------------
id          Long    (PK, autoincrement)
caseId      Long    (FK → cases.id, CASCADE)
interviewee String
date        String  (formato ISO-8601)
notes       String
findings    String
```

### Relación 1:N

- Un **Case** puede tener múltiples **Interview**.
- Cada **Interview** pertenece a un solo **Case**.
- La relación se implementa mediante `ForeignKey` en Room.
- **CASCADE**: Al eliminar un caso, todas sus entrevistas se eliminan automáticamente.

## Navegación

| Ruta | Pantalla | Descripción |
|---|---|---|
| `home` | HomeScreen | Dashboard principal |
| `cases` | CaseListScreen | Listado de casos |
| `case/{caseId}` | CaseDetailScreen | Detalle de un caso |
| `case/create` | CaseFormScreen | Crear caso nuevo |
| `case/edit/{caseId}` | CaseFormScreen | Editar caso existente |
| `case/{caseId}/interview/create` | InterviewFormScreen | Crear entrevista |

La navegación está centralizada en `AppNavigation.kt`. La barra de navegación inferior permite alternar entre Home y Casos.

## Base de datos

### Tablas

- **cases**: Almacena los casos criminales.
- **interviews**: Almacena las entrevistas asociadas a los casos.

### Motor

Room utiliza internamente SQLite. La base de datos se crea automáticamente en el primer acceso y se almacena en el dispositivo.

### Nombre de la base de datos

`journalist_note_database`

## Funcionalidades implementadas

- [x] Crear casos criminales
- [x] Editar casos criminales
- [x] Consultar casos (listado y detalle)
- [x] Eliminar casos (con confirmación)
- [x] Buscar casos por título
- [x] Visualizar estado de cada caso (Abierto, En investigación, Cerrado)
- [x] Registrar entrevistas asociadas a un caso
- [x] Consultar entrevistas de un caso
- [x] Registrar hallazgos de entrevistas
- [x] Registrar conclusión de casos
- [x] Mostrar resumen general de casos (Dashboard)
- [x] Eliminación en cascada de entrevistas al eliminar un caso

## Decisiones técnicas importantes

### MVC en Compose

En un proyecto con Jetpack Compose, la arquitectura MVC se adapta de la siguiente manera:
- **Model**: Entidades Room y DAOs que manejan la persistencia.
- **View**: Composables de Compose que renderizan la interfaz.
- **Controller**: Clases que conectan la UI con la capa de datos, encapsulando la lógica de negocio.

### Uso de Flow

Room devuelve `Flow` para consultas reactivas. Esto permite que la UI se actualice automáticamente cuando los datos cambian en la base de datos. Compose observa estos Flows usando `collectAsState()`.

### Eliminación en cascada

Se utiliza `ForeignKey` con `onDelete = ForeignKey.CASCADE` en la entidad `Interview`. Esto garantiza que al eliminar un caso, todas sus entrevistas se eliminen automáticamente, manteniendo la integridad referencial.

### Patrón Singleton para la base de datos

`AppDatabase` utiliza el patrón Singleton para garantizar que solo exista una instancia de la base de datos en toda la aplicación, evitando problemas de concurrencia.

### Scope de coroutines

Se utiliza `rememberCoroutineScope()` en los composable para ejecutar operaciones de base de datos sin bloquear el hilo principal.

## Cómo ejecutar el proyecto

1. Abrir el proyecto en Android Studio.
2. Sincronizar el proyecto con Gradle (File > Sync Project with Gradle Files).
3. Seleccionar un emulador o dispositivo físico.
4. Hacer clic en "Run" o presionar Shift+F10.
5. La aplicación se instalará y abrirá automáticamente.

### Requisitos mínimos

- Android Studio Hedgehog o posterior
- JDK 11+
- Android SDK 35
- Dispositivo o emulador con Android 7.0 (API 24) o posterior

## Posibles mejoras futuras

- [ ] Agregar imágenes o fotos a los casos y entrevistas.
- [ ] Implementar exportación de datos (PDF, CSV).
- [ ] Agregar filtros por estado y fecha en el listado.
- [ ] Implementar ordenamiento de casos por diferentes campos.
- [ ] Agregar un campo de ubicación a los casos.
- [ ] Implementar recordatorios y fechas límite.
- [ ] Agregar autenticación con PIN o biométrica.
- [ ] Implementar copia de seguridad de la base de datos.
- [ ] Agregar modo oscuro.
- [ ] Implementar búsqueda avanzada en entrevistas.
