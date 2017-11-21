package sample.Model;

import java.time.Duration;
import java.util.Random;

public class DistribucionGamma implements Distribucion {
    private double alfa;
    private double mu;

    public DistribucionGamma(double alfa, double mu) {
        this.alfa = alfa;
        this.mu = mu;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        Random generador = new Random();                                        //Inicializamos el generador de números aleatorios
        long quantil = (long)Math.pow((Gamma.gamma(alfa)*generador.nextDouble())/(Math.pow(mu,alfa)*Math.exp(-mu)),1-alfa);  //Generamos el quantil a partir de la probabilidad generada
        return Duration.ofMinutes(quantil);
    }

}
