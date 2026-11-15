package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.ReservarEntrada;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaReservaController {

    private final ReservarEntrada casoDeUso;

    public PantallaReservaController(ReservarEntrada casoDeUso) {
        this.casoDeUso = casoDeUso;
    }
}
