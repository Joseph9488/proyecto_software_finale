package co.edu.unillanos.campusfest.infrastructure.reloj;

import co.edu.unillanos.campusfest.application.port.Reloj;

import java.time.LocalDateTime;

/** PERSONA 2. La única clase que llama a now(). */
public class RelojDelSistema implements Reloj {

    @Override
    public LocalDateTime ahora() {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
