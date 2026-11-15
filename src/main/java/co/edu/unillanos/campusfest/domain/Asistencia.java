package co.edu.unillanos.campusfest.domain;

import java.time.LocalDateTime;

/** Registro de que una reserva confirmada ingresó al evento. Es inmutable. */
public class Asistencia {

    private final LocalDateTime hora;

    public Asistencia(LocalDateTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException("La hora de ingreso no puede ser nula.");
        }
        this.hora = hora;
    }

    public LocalDateTime getHora() {
        return hora;
    }
}