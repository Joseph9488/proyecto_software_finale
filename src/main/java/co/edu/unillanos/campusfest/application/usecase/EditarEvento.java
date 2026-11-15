package co.edu.unillanos.campusfest.application.usecase;

import co.edu.unillanos.campusfest.application.port.Reloj;
import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.domain.Evento;

import java.time.LocalDateTime;

/** PERSONA 2 (casos de uso). Solo coordina: las reglas viven en las entidades. */
public class EditarEvento {

    public EditarEvento(RepositorioEventos repositorio, Reloj reloj) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Evento obtener(String eventoId) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public void editar(String eventoId, String nombre, LocalDateTime fecha,
                       String lugar, int nuevoAforo) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
