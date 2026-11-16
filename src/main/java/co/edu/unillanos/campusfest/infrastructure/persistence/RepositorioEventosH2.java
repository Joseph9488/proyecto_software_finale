
package co.edu.unillanos.campusfest.infrastructure.persistence;

import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.domain.Asistencia;
import co.edu.unillanos.campusfest.domain.EstadoReserva;
import co.edu.unillanos.campusfest.domain.Evento;
import co.edu.unillanos.campusfest.domain.Reserva;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEventosH2 implements RepositorioEventos {

    private final Connection conexion;

    public RepositorioEventosH2(Connection conexion) {
        this.conexion = conexion;
        crearTablas();
    }

    private void crearTablas() {
        try (InputStream entrada = getClass()
                .getClassLoader()
                .getResourceAsStream("db/schema.sql")) {

            if (entrada == null) {
                throw new IllegalStateException(
                        "No se encontro db/schema.sql");
            }

            String sql = new String(
                    entrada.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            try (Statement sentencia = conexion.createStatement()) {
                for (String instruccion : sql.split(";")) {
                    if (!instruccion.isBlank()) {
                        sentencia.execute(instruccion.trim());
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new IllegalStateException(
                    "Error al crear las tablas H2", e);
        }
    }

    @Override
    public Evento buscar(String eventoId) {
        String sqlEvento =
                "SELECT * FROM EVENTO WHERE id = ?";

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sqlEvento)) {

            sentencia.setString(1, eventoId);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (!resultado.next()) {
                    return null;
                }

                String id = resultado.getString("id");
                String nombre = resultado.getString("nombre");
                Timestamp fecha = resultado.getTimestamp("fecha");
                String lugar = resultado.getString("lugar");
                int aforo = resultado.getInt("aforo");
                int bloqueados = resultado.getInt("bloqueados");

                List<Reserva> reservas = buscarReservas(id);

                return new Evento(
                        id,
                        nombre,
                        fecha.toLocalDateTime(),
                        lugar,
                        aforo,
                        bloqueados,
                        reservas
                );
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al buscar el evento", e);
        }
    }

    private List<Reserva> buscarReservas(String eventoId)
            throws SQLException {

        List<Reserva> reservas = new ArrayList<>();

        String sql =
                "SELECT r.codigo, r.estudiante_id, " +
                "r.vence_en, r.estado, a.hora " +
                "FROM RESERVA r " +
                "LEFT JOIN ASISTENCIA a " +
                "ON a.reserva_codigo = r.codigo " +
                "WHERE r.evento_id = ? " +
                "ORDER BY r.codigo";

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, eventoId);

            try (ResultSet resultado = sentencia.executeQuery()) {

                while (resultado.next()) {

                    String codigo =
                            resultado.getString("codigo");

                    String estudianteId =
                            resultado.getString("estudiante_id");

                    Timestamp vencimiento =
                            resultado.getTimestamp("vence_en");

                    EstadoReserva estado = EstadoReserva.valueOf(
                            resultado.getString("estado")
                    );

                    Timestamp hora =
                            resultado.getTimestamp("hora");

                    Asistencia asistencia = null;

                    if (hora != null) {
                        asistencia = new Asistencia(
                                hora.toLocalDateTime()
                        );
                    }

                    Reserva reserva = Reserva.reconstruir(
                            codigo,
                            estudianteId,
                            vencimiento.toLocalDateTime(),
                            estado,
                            asistencia
                    );

                    reservas.add(reserva);
                }
            }
        }

        return reservas;
    }

    @Override
    public void guardar(Evento evento) {

        boolean autocommitOriginal;

        try {
            autocommitOriginal = conexion.getAutoCommit();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Error al iniciar el guardado", e);
        }

        try {
            conexion.setAutoCommit(false);

            guardarEvento(evento);

            for (Reserva reserva : evento.getReservas()) {
                guardarReserva(evento.getId(), reserva);
                guardarAsistencia(reserva);
            }

            conexion.commit();

        } catch (SQLException e) {

            try {
                conexion.rollback();
            } catch (SQLException errorRollback) {
                e.addSuppressed(errorRollback);
            }

            throw new IllegalStateException(
                    "Error al guardar el evento", e);

        } finally {

            try {
                conexion.setAutoCommit(autocommitOriginal);
            } catch (SQLException e) {
                throw new IllegalStateException(
                        "Error al restaurar la conexion", e);
            }
        }
    }

    private void guardarEvento(Evento evento)
            throws SQLException {

        String sql =
                "MERGE INTO EVENTO " +
                "(id, nombre, fecha, lugar, aforo, bloqueados) " +
                "KEY(id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, evento.getId());
            sentencia.setString(2, evento.getNombre());
            sentencia.setTimestamp(
                    3, Timestamp.valueOf(evento.getFecha()));
            sentencia.setString(4, evento.getLugar());
            sentencia.setInt(5, evento.getAforo());
            sentencia.setInt(6, evento.getBloqueados());

            sentencia.executeUpdate();
        }
    }

    private void guardarReserva(String eventoId, Reserva reserva)
            throws SQLException {

        String sql =
                "MERGE INTO RESERVA " +
                "(codigo, evento_id, estudiante_id, vence_en, estado) " +
                "KEY(codigo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, reserva.getCodigo());
            sentencia.setString(2, eventoId);
            sentencia.setString(3, reserva.getEstudianteId());
            sentencia.setTimestamp(
                    4, Timestamp.valueOf(reserva.getVenceEn()));
            sentencia.setString(
                    5, reserva.getEstado().name());

            sentencia.executeUpdate();
        }
    }

    private void guardarAsistencia(Reserva reserva)
            throws SQLException {

        if (reserva.getAsistencia() == null) {
            String sql =
                    "DELETE FROM ASISTENCIA " +
                    "WHERE reserva_codigo = ?";

            try (PreparedStatement sentencia =
                         conexion.prepareStatement(sql)) {

                sentencia.setString(1, reserva.getCodigo());
                sentencia.executeUpdate();
            }

            return;
        }

        String sql =
                "MERGE INTO ASISTENCIA " +
                "(reserva_codigo, hora) " +
                "KEY(reserva_codigo) VALUES (?, ?)";

        try (PreparedStatement sentencia =
                     conexion.prepareStatement(sql)) {

            sentencia.setString(1, reserva.getCodigo());
            sentencia.setTimestamp(
                    2,
                    Timestamp.valueOf(
                            reserva.getAsistencia().getHora()
                    )
            );

            sentencia.executeUpdate();
        }
    }
}
