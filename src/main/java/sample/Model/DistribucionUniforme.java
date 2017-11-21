package sample.Model;

import java.time.Duration;
import java.util.Random;

public class DistribucionUniforme implements Distribucion {
    private double a;
    private double b;

    public DistribucionUniforme(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Duration calcular() {
        Random generador = new Random();                            //Inicializamos el generador de números aleatorios
        long quantil = (long)(a + generador.nextDouble() * (b-a));  //Generamos el quantil a partir de la probabilidad generada
        return Duration.ofMinutes(quantil);
    }
}
