package sample.Model;
import java.time.LocalTime;

public class Servidor implements Comparable{
    private Distribucion distribucionTiempoServicio;  // distribucion que describe el tiempo de servicio
    private LocalTime tiempoSalida; // tiempo de salida del trabajo actual
    private boolean ocupado;
    private String nombre;

    public Servidor(Distribucion distribucionTiempoServicio, String nombre) {
        this.distribucionTiempoServicio = distribucionTiempoServicio;
        ocupado = false;
        tiempoSalida = LocalTime.MAX;
        this.nombre = nombre;
    }

    public void asignarTrabajo(LocalTime tiempoActual) {
        ocupado = true; // ahora esta ocupado
        tiempoSalida = tiempoActual.plus(distribucionTiempoServicio.calcular()); // calcular su tiempo de salida
    }

    public void terminarTrabajo() {
        ocupado = false; // está ocioso
        tiempoSalida = LocalTime.MAX;  // conviene asignarlo al tiempo máximo para hacer comparaciones con otros servidores
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
}
