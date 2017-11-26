package Model;

import java.time.Duration;
import java.time.LocalTime;

public class GeneradorDeTrabajos {
    private Distribucion distribucionTiempoLlegadas;
    private LocalTime tiempoSiguienteLlegada;

    public GeneradorDeTrabajos() {
        this.distribucionTiempoLlegadas = new DistribucionPoisson(2);
    }

    public GeneradorDeTrabajos(Distribucion distribucionTiempoLlegadas) {
        this.distribucionTiempoLlegadas = distribucionTiempoLlegadas;
    }

    public void generarSiguienteLlegada(LocalTime tiempoActual) {
        tiempoSiguienteLlegada = tiempoActual.plus(Duration.ofNanos(Math.round(distribucionTiempoLlegadas.calcular()*60000000000L)));
    }

    public LocalTime getTiempoSiguienteLlegada() {
        return tiempoSiguienteLlegada;
    }
}
