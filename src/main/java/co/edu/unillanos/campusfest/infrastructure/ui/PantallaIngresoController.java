package co.edu.unillanos.campusfest.infrastructure.ui;

import co.edu.unillanos.campusfest.application.usecase.RegistrarIngreso;

/** PERSONA 4. Solo lee la pantalla, llama al caso de uso y muestra el resultado o el error. */
public class PantallaIngresoController {

    private final RegistrarIngreso casoDeUso;

    public PantallaIngresoController(RegistrarIngreso casoDeUso) {
        this.casoDeUso = casoDeUso;
    }
}
