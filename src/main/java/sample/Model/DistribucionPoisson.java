package sample.Model;

import java.time.Duration;
import java.util.Random;
import sample.Utils.Gamma;

public class DistribucionPoisson implements Distribucion{
    private double lambda;

    public DistribucionPoisson(double lambda) {
        this.lambda = lambda;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        Random generador = new Random();                                        //Inicializamos el generador de números aleatorios
        int U = generador.nextInt();
        int i = 0;
        double F = Math.exp(-lambda);
        while (U >= F){
            F += Math.exp(-lambda) * (lambda ^ (i/Gamma.gamma(i+1)));
            i += 1;
        }
        long quantil = i;  //Generamos el quantil a partir de la probabilidad generada
        return Duration.ofMinutes(quantil);
    }

}
