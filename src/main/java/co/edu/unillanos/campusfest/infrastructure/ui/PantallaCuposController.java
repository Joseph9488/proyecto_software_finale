package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.ConsultarCupos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaCuposController {

    private final ConsultarCupos casoDeUso;

    @FXML
    private TextField campoEventoId;

    @FXML
    private Label etiquetaMensaje;

    public PantallaCuposController(ConsultarCupos casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    @FXML
    private void alConsultar() {
        try {
            String eventoId = campoEventoId.getText().trim();
            int cupos = casoDeUso.consultar(eventoId);
            etiquetaMensaje.setText("Cupos disponibles: " + cupos);
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }
}
