package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.EditarEvento;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaEventoController {

    private final EditarEvento casoDeUso;

    public PantallaEventoController(EditarEvento casoDeUso) {
        this.casoDeUso = casoDeUso;
    }
}
