package co.edu.unillanos.campusfest.infrastructure.persistence;

import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.domain.Evento;

import java.sql.Connection;

/**
 * PERSONA 3. Crea las tablas con db/schema.sql y convierte todo SQLException
 * en IllegalStateException: ver CONTRATO.md.
 */
public class RepositorioEventosH2 implements RepositorioEventos {

    public RepositorioEventosH2(Connection conexion) {
        throw new UnsupportedOperationException("Pendiente de implementar");
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
