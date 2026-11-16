package co.edu.unillanos.campusfest;

import co.edu.unillanos.campusfest.application.port.Reloj;
import co.edu.unillanos.campusfest.application.port.RepositorioEventos;
import co.edu.unillanos.campusfest.application.usecase.ConsultarCupos;
import co.edu.unillanos.campusfest.application.usecase.EditarEvento;
import co.edu.unillanos.campusfest.application.usecase.RegistrarIngreso;
import co.edu.unillanos.campusfest.application.usecase.ReservarEntrada;
import co.edu.unillanos.campusfest.domain.Evento;
import co.edu.unillanos.campusfest.infrastructure.persistence.RepositorioEventosEnMemoria;
import co.edu.unillanos.campusfest.infrastructure.reloj.RelojDelSistema;
import co.edu.unillanos.campusfest.infrastructure.ui.PantallaCuposController;
import co.edu.unillanos.campusfest.infrastructure.ui.PantallaEventoController;
import co.edu.unillanos.campusfest.infrastructure.ui.PantallaIngresoController;
import co.edu.unillanos.campusfest.infrastructure.ui.PantallaReservaController;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * PERSONA 4. Única clase que crea RepositorioEventosH2, RepositorioEventosEnMemoria,
 * RelojDelSistema, los 4 casos de uso y los 4 controladores: ver CONTRATO.md.
 */
public class App extends Application {

    private static final String ID_EVENTO_EJEMPLO = "ev-1";

    @Override
    public void start(Stage escenario) throws IOException {
        RepositorioEventos repositorio = new RepositorioEventosEnMemoria();
        Reloj reloj = new RelojDelSistema();
        cargarEventoDeEjemplo(repositorio);

        TabPane pestanas = crearPestanas(repositorio, reloj);
        escenario.setTitle("CampusFest");
        escenario.setScene(new Scene(pestanas, 480, 560));
        escenario.show();
    }

    private void cargarEventoDeEjemplo(RepositorioEventos repositorio) {
        if (repositorio.buscar(ID_EVENTO_EJEMPLO) == null) {
            Evento evento = new Evento(ID_EVENTO_EJEMPLO, "Concierto de bienvenida",
                    LocalDateTime.of(2026, 11, 28, 19, 0), "Auditorio Barcelona", 100, 10);
            repositorio.guardar(evento);
        }
    }

    private TabPane crearPestanas(RepositorioEventos repositorio, Reloj reloj) throws IOException {
        ConsultarCupos consultarCupos = new ConsultarCupos(repositorio, reloj);
        ReservarEntrada reservarEntrada = new ReservarEntrada(repositorio, reloj);
        EditarEvento editarEvento = new EditarEvento(repositorio, reloj);
        RegistrarIngreso registrarIngreso = new RegistrarIngreso(repositorio, reloj);

        TabPane pestanas = new TabPane();
        pestanas.getTabs().add(crearPestana("Consultar cupos", "pantalla-cupos.fxml",
                new PantallaCuposController(consultarCupos)));
        pestanas.getTabs().add(crearPestana("Reservar", "pantalla-reserva.fxml",
                new PantallaReservaController(reservarEntrada)));
        pestanas.getTabs().add(crearPestana("Editar evento", "pantalla-evento.fxml",
                new PantallaEventoController(editarEvento)));
        pestanas.getTabs().add(crearPestana("Registrar ingreso", "pantalla-ingreso.fxml",
                new PantallaIngresoController(registrarIngreso)));
        return pestanas;
    }

    private Tab crearPestana(String titulo, String archivoFxml, Object controlador) throws IOException {
        FXMLLoader cargador = new FXMLLoader(App.class.getResource("vistas/" + archivoFxml));
        cargador.setController(controlador);
        Tab pestana = new Tab(titulo, cargador.load());
        pestana.setClosable(false);
        return pestana;
    }
}
