package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.EditarEvento;
import co.edu.unillanos.campusfest.domain.Evento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaEventoController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final EditarEvento casoDeUso;

    @FXML
    private TextField campoEventoId;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoFecha;

    @FXML
    private TextField campoLugar;

    @FXML
    private TextField campoAforo;

    @FXML
    private Label etiquetaMensaje;

    public PantallaEventoController(EditarEvento casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    @FXML
    private void alCargar() {
        try {
            String eventoId = campoEventoId.getText().trim();
            Evento evento = casoDeUso.obtener(eventoId);
            campoNombre.setText(evento.getNombre());
            campoFecha.setText(evento.getFecha().format(FORMATO_FECHA));
            campoLugar.setText(evento.getLugar());
            campoAforo.setText(String.valueOf(evento.getAforo()));
            etiquetaMensaje.setText("Evento cargado.");
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void alGuardar() {
        try {
            String eventoId = campoEventoId.getText().trim();
            String nombre = campoNombre.getText().trim();
            LocalDateTime fecha = leerFecha();
            String lugar = campoLugar.getText().trim();
            int aforo = leerAforo();
            casoDeUso.editar(eventoId, nombre, fecha, lugar, aforo);
            etiquetaMensaje.setText("Evento actualizado.");
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }

    private LocalDateTime leerFecha() {
        try {
            return LocalDateTime.parse(campoFecha.getText().trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato dd/MM/yyyy HH:mm, por ejemplo 28/11/2026 19:00.");
        }
    }

    private int leerAforo() {
        try {
            return Integer.parseInt(campoAforo.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El aforo debe ser un número entero.");
        }
    }
}
