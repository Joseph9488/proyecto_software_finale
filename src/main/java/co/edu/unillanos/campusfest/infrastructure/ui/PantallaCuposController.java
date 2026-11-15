package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.ConsultarCupos;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaCuposController {

    private final ConsultarCupos casoDeUso;

    public PantallaCuposController(ConsultarCupos casoDeUso) {
        this.casoDeUso = casoDeUso;
    }
}
