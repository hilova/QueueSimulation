package Model;

import java.util.Random;

public class DistribucionExponencial implements Distribucion {
    private double lambda;

    public DistribucionExponencial(double lambda) {
        this.lambda = lambda;
    }


    public double calcular() {
        Random generador = new Random();                                        //Inicializamos el generador de números aleatorios
        double quantil = (-1 * Math.log(1-generador.nextDouble())/lambda);  //Generamos el quantil a partir de la probabilidad generada
        return quantil;
    }

    public String getName(){
        return "exponencial";
    }
}
