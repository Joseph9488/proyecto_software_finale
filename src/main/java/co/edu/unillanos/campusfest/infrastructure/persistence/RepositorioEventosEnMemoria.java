package co.edu.unillanos.campusfest.infrastructure.persistence;

import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.domain.Evento;

/** PERSONA 2. */
public class RepositorioEventosEnMemoria implements RepositorioEventos {

    public RepositorioEventosEnMemoria() {
    }

    @Override
    public Evento buscar(String eventoId) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    @Override
    public void guardar(Evento evento) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
