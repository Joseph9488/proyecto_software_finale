package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.ReservarEntrada;
import co.edu.unillanos.campusfest.domain.Reserva;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaReservaController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final ReservarEntrada casoDeUso;

    @FXML
    private TextField campoEventoId;

    @FXML
    private TextField campoEstudianteId;

    @FXML
    private TextField campoCodigoReserva;

    @FXML
    private Label etiquetaMensaje;

    public PantallaReservaController(ReservarEntrada casoDeUso) {
        this.casoDeUso = casoDeUso;
    }

    @FXML
    private void alSolicitarReserva() {
        try {
            String eventoId = campoEventoId.getText().trim();
            String estudianteId = campoEstudianteId.getText().trim();
            Reserva reserva = casoDeUso.solicitarReserva(eventoId, estudianteId);
            campoCodigoReserva.setText(reserva.getCodigo());
            etiquetaMensaje.setText("Reserva creada. Código: " + reserva.getCodigo()
                    + ". Vence el " + reserva.getVenceEn().format(FORMATO_FECHA) + ".");
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }

    @FXML
    private void alConfirmarReserva() {
        try {
            String eventoId = campoEventoId.getText().trim();
            String codigo = campoCodigoReserva.getText().trim();
            casoDeUso.confirmarReserva(eventoId, codigo);
            etiquetaMensaje.setText("Reserva confirmada.");
        } catch (IllegalStateException e) {
            etiquetaMensaje.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            etiquetaMensaje.setText(e.getMessage());
        }
    }
}
