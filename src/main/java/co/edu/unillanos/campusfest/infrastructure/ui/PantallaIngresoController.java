package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.RegistrarIngreso;
import co.edu.unillanos.campusfest.domain.Asistencia;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaIngresoController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final RegistrarIngreso casoDeUso;

    @FXML
    private TextField campoEventoId;

    @FXML
    private TextField campoCodigo;

    @FXML
    private Label etiquetaMensaje;

    public PantallaIngresoController(RegistrarIngreso casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    @FXML
    private void alRegistrar() {
        try {
            String eventoId = campoEventoId.getText().trim();
            String codigo = campoCodigo.getText().trim();
            Asistencia asistencia = casoDeUso.registrar(eventoId, codigo);
            etiquetaMensaje.setText("Ingreso registrado el " + asistencia.getHora().format(FORMATO_FECHA) + ".");
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }
}
