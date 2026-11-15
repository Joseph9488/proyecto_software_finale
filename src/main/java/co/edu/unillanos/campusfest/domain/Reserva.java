package co.edu.unillanos.campusfest.domain;

import java.time.LocalDateTime;

/** PERSONA 1 (entidades). Plazo de 10 minutos y estados: ver CONTRATO.md. */
public class Reserva {

    public static Reserva pendiente(String estudianteId, LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /** Para el adaptador H2: no aplica reglas. */
    public static Reserva reconstruir(String codigo, String estudianteId, LocalDateTime venceEn,
                                      EstadoReserva estado, Asistencia asistencia) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public boolean ocupaCupo(LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public void confirmar(LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public void cancelar() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public Asistencia registrarIngreso(LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public String getCodigo() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public String getEstudianteId() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public LocalDateTime getVenceEn() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    public EstadoReserva getEstado() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /** null si no ha ingresado. */
    public Asistencia getAsistencia() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
