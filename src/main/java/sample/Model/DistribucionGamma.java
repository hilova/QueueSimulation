package sample.Model;

import main.java.sample.Model.Gamma;

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
        Random generador = new Random();
        double randomNumber = generador.nextDouble();//Inicializamos el generador de números aleatorios
        long quantil = (long)Math.pow((Gamma.gamma(alfa)*randomNumber)/(Math.pow(mu,alfa)*Math.exp(-mu)),1/(alfa-1));  //Generamos el quantil a partir de la probabilidad generada
        return Duration.ofMinutes(quantil);
    }

}
