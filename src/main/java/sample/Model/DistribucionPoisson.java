package sample.Model;

import java.time.Duration;

public class DistribucionPoisson implements Distribucion{
    private double mu;

    public DistribucionPoisson(double mu) {
        this.mu = mu;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        return Duration.ofHours(1);
    }
}
