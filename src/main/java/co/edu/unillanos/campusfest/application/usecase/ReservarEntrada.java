package co.edu.unillanos.campusfest.application.usecase;

import co.edu.unillanos.campusfest.application.port.Reloj;
import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.domain.Reserva;

/** PERSONA 2 (casos de uso). Solo coordina: las reglas viven en las entidades. */
public class ReservarEntrada {

    public ReservarEntrada(RepositorioEventos repositorio, Reloj reloj) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Reserva solicitarReserva(String eventoId, String estudianteId) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Reserva confirmarReserva(String eventoId, String codigoReserva) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
