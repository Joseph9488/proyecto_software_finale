package co.edu.unillanos.campusfest.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entrada reservada por un estudiante para un evento.
 *
 * Estados: PENDIENTE pasa a CONFIRMADA (antes de 10 minutos) o a VENCIDA;
 * PENDIENTE o CONFIRMADA pasan a CANCELADA. El estado solo cambia con
 * los métodos de esta clase, nunca con setters.
 */
public class Reserva {

    private static final int MINUTOS_DE_PLAZO = 10;

    private final String codigo;
    private final String estudianteId;
    private final LocalDateTime venceEn;
    private EstadoReserva estado;
    private Asistencia asistencia;

    private Reserva(String codigo, String estudianteId, LocalDateTime venceEn,
                    EstadoReserva estado, Asistencia asistencia) {
        this.codigo = codigo;
        this.estudianteId = estudianteId;
        this.venceEn = venceEn;
        this.estado = estado;
        this.asistencia = asistencia;
    }

    /** Crea una reserva pendiente que vence 10 minutos después de ahora. */
    public static Reserva pendiente(String estudianteId, LocalDateTime ahora) {
        if (estudianteId == null || estudianteId.isBlank()) {
            throw new IllegalArgumentException("El estudiante no puede estar vacío.");
        }
        String codigo = UUID.randomUUID().toString();
        LocalDateTime venceEn = ahora.plusMinutes(MINUTOS_DE_PLAZO);
        return new Reserva(codigo, estudianteId, venceEn, EstadoReserva.PENDIENTE, null);
    }

    /** Para el adaptador H2: arma la reserva con lo guardado, sin aplicar reglas. */
    public static Reserva reconstruir(String codigo, String estudianteId, LocalDateTime venceEn,
                                      EstadoReserva estado, Asistencia asistencia) {
        return new Reserva(codigo, estudianteId, venceEn, estado, asistencia);
    }

    /**
     * Una confirmada siempre ocupa cupo. Una pendiente ocupa cupo solo mientras
     * no llegue la hora de vencimiento (a los 10:00 exactos ya no ocupa).
     */
    public boolean ocupaCupo(LocalDateTime ahora) {
        if (estado == EstadoReserva.CONFIRMADA) {
            return true;
        }
        if (estado == EstadoReserva.PENDIENTE) {
            return ahora.isBefore(venceEn);
        }
        return false;
    }

    public void confirmar(LocalDateTime ahora) {
        if (estado != EstadoReserva.PENDIENTE) {
            throw new IllegalStateException("Solo se puede confirmar una reserva pendiente.");
        }
        if (!ahora.isBefore(venceEn)) {
            throw new IllegalStateException("La reserva venció: el plazo de "
                    + MINUTOS_DE_PLAZO + " minutos terminó.");
        }
        estado = EstadoReserva.CONFIRMADA;
    }

    public void cancelar() {
        if (estado != EstadoReserva.PENDIENTE && estado != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException("Esta reserva ya no se puede cancelar.");
        }
        estado = EstadoReserva.CANCELADA;
    }

    /** Un solo ingreso por reserva confirmada. */
    public Asistencia registrarIngreso(LocalDateTime ahora) {
        if (estado != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException("Solo una reserva confirmada puede ingresar.");
        }
        if (asistencia != null) {
            throw new IllegalStateException("Esta reserva ya registró su ingreso a las "
                    + asistencia.getHora() + ".");
        }
        asistencia = new Asistencia(ahora);
        return asistencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public LocalDateTime getVenceEn() {
        return venceEn;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    /** null si no ha ingresado. */
    public Asistencia getAsistencia() {
        return asistencia;
    }
}