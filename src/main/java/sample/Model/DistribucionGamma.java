package sample.Model;

import java.time.Duration;

public class DistribucionGamma implements Distribucion {
    private double alfa;
    private double mu;

    public DistribucionGamma(double alfa, double mu) {
        this.alfa = alfa;
        this.mu = mu;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        return Duration.ofHours(1);
    }

}
