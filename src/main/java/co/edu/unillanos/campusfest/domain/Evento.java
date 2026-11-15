package co.edu.unillanos.campusfest.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Evento con aforo y reservas. Es el experto en los cupos: calcula la
 * disponibilidad, crea las reservas y las busca por su código.
 *
 * Cupos disponibles = aforo - bloqueados - cupos ocupados por reservas.
 * Aforo mínimo = bloqueados + cupos ocupados por reservas.
 */
public class Evento {

    private final String id;
    private String nombre;
    private LocalDateTime fecha;
    private String lugar;
    private int aforo;
    private final int bloqueados;
    private final List<Reserva> reservas;

    public Evento(String id, String nombre, LocalDateTime fecha, String lugar,
                  int aforo, int bloqueados) {
        this(id, nombre, fecha, lugar, aforo, bloqueados, new ArrayList<>());
    }

    /** Para el adaptador H2: el evento llega con sus reservas ya guardadas. */
    public Evento(String id, String nombre, LocalDateTime fecha, String lugar,
                  int aforo, int bloqueados, List<Reserva> reservas) {
        validarTexto(id, "El identificador");
        validarDatos(nombre, fecha, lugar);
        if (aforo < 0 || bloqueados < 0) {
            throw new IllegalArgumentException("El aforo y los bloqueados no pueden ser negativos.");
        }
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.aforo = aforo;
        this.bloqueados = bloqueados;
        this.reservas = new ArrayList<>(reservas);
    }

    public int cuposDisponibles(LocalDateTime ahora) {
        return aforo - bloqueados - cuposOcupadosPorReservas(ahora);
    }

    /** Crea una reserva pendiente si queda al menos un cupo libre. */
    public Reserva reservar(String estudianteId, LocalDateTime ahora) {
        if (cuposDisponibles(ahora) <= 0) {
            throw new IllegalStateException("No hay cupos disponibles para " + nombre + ".");
        }
        Reserva reserva = Reserva.pendiente(estudianteId, ahora);
        reservas.add(reserva);
        return reserva;
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        for (Reserva reserva : reservas) {
            if (reserva.getCodigo().equals(codigo)) {
                return reserva;
            }
        }
        throw new IllegalArgumentException("El código no pertenece a una reserva de este evento.");
    }

    public void cambiarDatos(String nombre, LocalDateTime fecha, String lugar) {
        validarDatos(nombre, fecha, lugar);
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    /** El aforo no puede quedar por debajo de bloqueados + cupos ocupados por reservas. */
    public void cambiarAforo(int nuevoAforo, LocalDateTime ahora) {
        int ocupados = cuposOcupadosPorReservas(ahora);
        int aforoMinimo = bloqueados + ocupados;
        if (nuevoAforo < aforoMinimo) {
            throw new IllegalStateException("Hay " + ocupados + " cupos ocupados por reservas y "
                    + bloqueados + " bloqueados: el aforo no puede ser menor que "
                    + aforoMinimo + ".");
        }
        aforo = nuevoAforo;
    }

    private int cuposOcupadosPorReservas(LocalDateTime ahora) {
        int ocupados = 0;
        for (Reserva reserva : reservas) {
            if (reserva.ocupaCupo(ahora)) {
                ocupados++;
            }
        }
        return ocupados;
    }

    private static void validarDatos(String nombre, LocalDateTime fecha, String lugar) {
        validarTexto(nombre, "El nombre");
        validarTexto(lugar, "El lugar");
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacía.");
        }
    }

    private static void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public int getAforo() {
        return aforo;
    }

    public int getBloqueados() {
        return bloqueados;
    }

    /** Lista de solo lectura. */
    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }
}