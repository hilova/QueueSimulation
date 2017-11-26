package Model;

import java.util.Random;

public class DistribucionGamma implements Distribucion {
    private double alfa;
    private double mu;

    public DistribucionGamma(double alfa, double mu) {
        this.alfa = alfa;
        this.mu = mu;
    }

    public double calcular() {
        Random generador = new Random();
        double randomNumber = generador.nextDouble();//Inicializamos el generador de números aleatorios
        double quantil = Math.pow((Gamma.gamma(alfa)*randomNumber)/(Math.pow(mu,alfa)*Math.exp(-mu)),1/(alfa-1));  //Generamos el quantil a partir de la probabilidad generada
        return quantil;
    }

    public String getName(){
        return "gamma";
    }

}
