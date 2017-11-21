package sample.Model;

import java.time.Duration;
import java.util.Random;

public class DistribucionPoisson implements Distribucion{
    private double lambda;

    public DistribucionPoisson(double lambda) {
        this.lambda = lambda;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        Random generador = new Random();                                        //Inicializamos el generador de números aleatorios

        long quantil = (long)(-1 * Math.log(1-generador.nextDouble())/lambda);  //Generamos el quantil a partir de la probabilidad generada
        return Duration.ofMinutes(quantil);
    }

}
