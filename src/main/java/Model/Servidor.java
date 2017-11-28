package Model;
import java.time.Duration;
import java.time.LocalTime;

public class Servidor implements Comparable{
    private Distribucion distribucionTiempoServicio;  // distribucion que describe el tiempo de servicio
    private LocalTime tiempoSalida; // tiempo de salida del trabajo actual
    private Duration tiempoServicio;
    private LocalTime tiempoDeEntradaAlSistema;
    private LocalTime tiempoDeInicioDeOcio;
    private boolean ocupado;
    private String nombre;

    public Servidor(Distribucion distribucionTiempoServicio, String nombre) {
        this.distribucionTiempoServicio = distribucionTiempoServicio;
        ocupado = false;
        tiempoSalida = LocalTime.MAX;
        this.nombre = nombre;
    }

    public LocalTime getTiempoDeEntradaAlSistema(){
        return tiempoDeEntradaAlSistema;
    }

    public void asignarTrabajo(LocalTime tiempoActual) {
        ocupado = true; // ahora esta ocupado
        tiempoDeEntradaAlSistema = tiempoActual;
        // Duration requiere un int, se transforma a nanosegundos para obtener la mejor precisión
        tiempoServicio = Duration.ofNanos(Math.round(distribucionTiempoServicio.calcular()*60000000000L));
        tiempoSalida = tiempoActual.plus(tiempoServicio); // calcular su tiempo de salida
        tiempoDeInicioDeOcio = null;
    }

    // para poder registrar los tiempos de respuesta, recibe el tiempo en que llegó el trabajo
    public void asignarTrabajo(LocalTime tiempoActual, LocalTime tiempoDeEntradaAlSistema) {
        ocupado = true; // ahora esta ocupado
        this.tiempoDeEntradaAlSistema = LocalTime.from(tiempoDeEntradaAlSistema);
        // Duration requiere un int, se transforma a nanosegundos para obtener la mejor precisión
        tiempoServicio = Duration.ofNanos(Math.round(distribucionTiempoServicio.calcular()*60000000000L));
        tiempoSalida = tiempoActual.plus(tiempoServicio); // calcular su tiempo de salida
        tiempoDeInicioDeOcio = null;
    }

    public void terminarTrabajo(LocalTime tiempoActual) {
        ocupado = false; // está ocioso
        tiempoDeInicioDeOcio = LocalTime.from(tiempoActual);
        tiempoSalida = null;
    }

    public LocalTime getTiempoSalida() {
        return tiempoSalida;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    // determina cual servidor tiene un tiempo de salida más temprano
    // se utilizará en la simulación para mantener los servidores en orden de más termprano a más tardío
    public int compareTo(Object o) {
        Servidor otroServidor = (Servidor) o;
        return tiempoSalida.compareTo(otroServidor.getTiempoSalida());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getTiempoDeInicioDeOcio() {
        return tiempoDeInicioDeOcio;
    }
}
