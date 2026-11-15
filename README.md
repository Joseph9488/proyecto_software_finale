# CampusFest

Proyecto de semestre: reservas de entradas a eventos universitarios (Java 21, JavaFX 21, H2, Maven).

## Integrantes
- (Joseph Martinez) - Entidades (`domain`)
- (nombre 2) - Casos de uso, puertos, reloj y repositorio en memoria
- (nombre 3) - Persistencia H2
- Sebastián Hernández - Pantallas JavaFX, `App` y `Launcher`

## Cómo ejecutar
- Pruebas: `mvn test`
- Aplicación: `mvn javafx:run`

## Casos de uso implementados
- CU1 Consultar cupos - Hecho
- CU2 Reservar y confirmar una entrada
- CU3 Editar evento
- CU4 Registrar el ingreso con QR

(Marcar cuáles están terminados.)

## Declaración de uso de IA
- Herramientas usadas, para qué y en qué clases: 
1. Usado claude para la revision de codigo y revisar que las clases entidad, asistencia y reserva para que cumplan con los requisitos minimos, tomando en cuenta la arquitectura limpia y algunos conceptos pidiendo a cada momento explicaciones de cada accion tomada.
- Al menos una propuesta de la IA que violaba el diseño y cómo se corrigió: 
1. En reserva la clase entidad, la denegación de despues de los 10 minutos no estaba, no aparecia a pesar de ser bastante claro en la inclusion del mismo, se corrigio especificandole y siendo mas preciso modificando el codigo para su correcta funcion.

## Reglas del equipo
- Las pruebas de `src/test/java` no se modifican, no se borran ni se desactivan.
- Cada quien trabaja en su rama y abre un pull request que revisa otro integrante.
