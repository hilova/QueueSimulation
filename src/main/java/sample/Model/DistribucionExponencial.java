package sample.Model;

import java.time.Duration;

public class DistribucionExponencial implements Distribucion {
    private double mu;

    public DistribucionExponencial(double mu) {
        this.mu = mu;
    }


    public Duration calcular() {
        // TODO calcular distribucion
        return Duration.ofHours(1);
    }

}
