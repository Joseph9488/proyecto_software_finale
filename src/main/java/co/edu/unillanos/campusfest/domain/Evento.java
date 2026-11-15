package co.edu.unillanos.campusfest.domain;

import java.time.LocalDateTime;
import java.util.List;

/** PERSONA 1 (entidades). Cupos, reservas y aforo: ver CONTRATO.md. */
public class Evento {

    public Evento(String id, String nombre, LocalDateTime fecha, String lugar,
                  int aforo, int bloqueados) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /** Para el adaptador H2: el evento llega con sus reservas. */
    public Evento(String id, String nombre, LocalDateTime fecha, String lugar,
                  int aforo, int bloqueados, List<Reserva> reservas) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public int cuposDisponibles(LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Reserva reservar(String estudianteId, LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public void cambiarDatos(String nombre, LocalDateTime fecha, String lugar) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public void cambiarAforo(int nuevoAforo, LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public String getId() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public String getNombre() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public LocalDateTime getFecha() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public String getLugar() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public int getAforo() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public int getBloqueados() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /** Lista de solo lectura. */
    public List<Reserva> getReservas() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
