# CampusFest

Proyecto de semestre: reservas de entradas a eventos universitarios (Java 21, JavaFX 21, H2, Maven).

## Integrantes
- (Joseph Martinez) - Entidades (`domain`)
- (Javier Vesga) - Casos de uso, puertos, reloj y repositorio en memoria
- (Willy Stiven Sáenz Betancourt) - Persistencia H2
- Sebastián Hernández - Pantallas JavaFX, `App` y `Launcher`

## Cómo ejecutar
- Pruebas: `mvn test`
- Aplicación: `mvn javafx:run`

## Casos de uso implementados
- CU1 Consultar cupos - Hecho
- CU2 Reservar y confirmar una entrada - Hecho
- CU3 Editar evento - Hecho
- CU4 Registrar el ingreso con QR - Hecho

(Marcar cuáles están terminados.)

## Declaración de uso de IA

### Joseph Martinez (entidades)

- Herramientas usadas, para qué y en qué clases: 
1. Usado claude para la revision de codigo y revisar que las clases entidad, asistencia y reserva para que cumplan con los requisitos minimos, tomando en cuenta la arquitectura limpia y algunos conceptos pidiendo a cada momento explicaciones de cada accion tomada.

- Al menos una propuesta de la IA que violaba el diseño y cómo se corrigió: 
1. En reserva la clase entidad, la denegación de despues de los 10 minutos no estaba, no aparecia a pesar de ser bastante claro en la inclusion del mismo, se corrigio especificandole y siendo mas preciso modificando el codigo para su correcta funcion.

### Javier Vesga (casos de uso, reloj y repositorio en memoria)
- Herramientas usadas, para qué y en qué clases: Usé Claude como apoyo en dos frentes. Para Git y GitHub (ramas, commits, push, autenticación y pull request). Y como apoyo para el código base de "ConsultarCupos", "ReservarEntrada", "EditarEvento", "RegistrarIngreso", "RelojDelSistema" y "RepositorioEventosEnMemoria", que estaban como esqueletos. Mi trabajo fue integrar ese código al proyecto, contrastarlo con el "CONTRATO.md", diagnosticar por qué fallaban las pruebas (leí los reportes de "surefire" y encontré que la causa común eran los esqueletos con "Pendiente de implementar"), corregir los problemas de integración y validar todo hasta que pasaron las 27 pruebas de los docentes.

- Propuesta de la IA que violaba el diseño y cómo se corrigió: Claude me indicó crear "RepositorioEventosEnMemoria" y "RelojDelSistema" en "application/port", pero por la arquitectura limpia van en "infrastructure/persistence" e "infrastructure/reloj". Detecté el problema porque el "package" no coincidía con la carpeta y VS Code marcó errores. Al revisar "git status" vi que además los puertos "Reloj" y "RepositorioEventos" aparecían borrados. Eliminé las copias mal ubicadas, restauré los puertos con "git restore" y dejé el código en las carpetas correctas.

Además, con ayuda de Claude detecté que buscarReservaPorCodigo ya lanza la excepción, por lo que retiré dos comprobaciones de null redundantes, y que EditarEvento modificaba los datos antes de validar el aforo, por lo que reordené las llamadas. Apliqué ambos ajustes y los validé con las pruebas.

### Willy Stiven Saenz Betancourt (persistencia H2)
Utilicé ChatGPT para apoyar la implementación de RepositorioEventosH2.java y la creación de db/schema.sql. La herramienta me ayudó a estructurar las tablas EVENTO, RESERVA y ASISTENCIA, establecer sus claves primarias y foráneas e implementar los métodos para guardar, buscar y actualizar los datos sin duplicarlos.

Durante el desarrollo, identifiqué que el ensamblaje de la aplicación en App.java todavía estaba incompleto, lo que ocasionaba el fallo de una prueba general. Para respetar la arquitectura hexagonal y las responsabilidades asignadas, mantuve la implementación de persistencia dentro de infrastructure.persistence y el esquema SQL en resources/db, sin modificar las clases de dominio ni los casos de uso.

## Reglas del equipo
- Las pruebas de `src/test/java` no se modifican, no se borran ni se desactivan.
- Cada quien trabaja en su rama y abre un pull request que revisa otro integrante.
