package sample.Model;

import java.time.Duration;

public class DistribucionUniforme implements Distribucion {
    private double a;
    private double b;

    public DistribucionUniforme(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Duration calcular() {
        // TODO calcular distribucion
        return Duration.ofHours(1);
    }
}
