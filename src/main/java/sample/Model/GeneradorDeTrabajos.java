package sample.Model;

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
        tiempoSiguienteLlegada = tiempoActual.plus(distribucionTiempoLlegadas.calcular());
    }

    public LocalTime getTiempoSiguienteLlegada() {
        return tiempoSiguienteLlegada;
    }
}
