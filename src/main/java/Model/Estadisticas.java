package Model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Estadisticas {
    // SE REGISTRAN LOS TOTALES PARA CALCULAR LOS DATOS AL FINALIZAR
    // TODO (a) the expected waiting time for a randomly selected job
    private Duration tiempoEsperandoEnColaTotal;
    private LinkedList<Duration> duracionesEnCola;

    // TODO (b) the expected response time;
    private Duration tiempoDeRespuestaTotal;
    private LinkedList<Duration> duracionesDeRespuesta;

    // TODO (c) the expected length of a queue (excluding the jobs receiving service), when a new job arrives
    private double tamañoDeColaTotalPorLlegada;

    // TODO (d) the expected maximum waiting time during a 10-hour day
    private Duration tiempoDeEsperaMaximoTotal;

    // TODO (e) the expected maximum length of a queue during a 10-hour day
    private Duration tiempoDeEsperaMinimoTotal;

    // TODO (f) the probability that at least one server is available, when a job arrives
    private double vecesUnServidorDisponibleTotales;

    // TODO (g) the probability that at least two servers are available, when a job arrives
    private double vecesDosServidoresDisponiblesTotales;

    // (h) the expected number of jobs processed by each server
    private HashMap<String, Integer> trabajosProcesadosTotalesPorServidor = new HashMap<>();

    // TODO (i) the expected time each server is idle during the day
    private HashMap<String, Duration> tiempoOciosoTotalesPorServidor = new HashMap<>();

    // (j) the expected number of jobs still remaining in the system at 6:03 pm
    private double trabajosRestantesAlFinalizarTotales;

    public Estadisticas() {
        tiempoEsperandoEnColaTotal = Duration.ZERO;
        tiempoDeRespuestaTotal = Duration.ZERO;
        tamañoDeColaTotalPorLlegada = 0;
        tiempoDeEsperaMaximoTotal = Duration.ZERO;
        tiempoDeEsperaMinimoTotal = Duration.ZERO;
        vecesUnServidorDisponibleTotales = 0;
        vecesDosServidoresDisponiblesTotales = 0;
        trabajosProcesadosTotalesPorServidor = new HashMap<>();
        tiempoOciosoTotalesPorServidor = new HashMap<>();
        trabajosRestantesAlFinalizarTotales = 0;
        trabajosAbandonaronColaTotales = 0;
        simulacionesEjecutadas = 0;
        llegadasTotales = 0;
        trabajosFinalizadosTotales = 0;
        duracionesEnCola = new LinkedList<Duration>();
        duracionesDeRespuesta = new LinkedList<Duration>();
    }

    // TODO (k) the expected percentage of jobs that left the queue prematurely
    private double trabajosAbandonaronColaTotales;

    // para calcular (d), (e), (h), (i), (j)
    private int simulacionesEjecutadas;

    // para calcular (c), (f), (g), (k)
    private int llegadasTotales;

    // para calcular (b)
    private int trabajosFinalizadosTotales;


    //Registra una llegada nueva
    public void añadirLlegadaNueva(){
        llegadasTotales++;
    }

    //Registra un tiempo de espera en la cola. Nota: Si no pasó por cola el tiempo es 0.
    public void añadirTiempoDeEsperaEnCola(Duration esperaEnCola){
        duracionesEnCola.add(esperaEnCola);
    }

    // TODO enviar tiempo de respuesta en SistemaColas.java
    public void añadirTrabajoFinalizado(String nombreServidor, LocalTime tiempoDeEntrada, LocalTime tiempoDeSalida){
        if (trabajosProcesadosTotalesPorServidor.containsKey(nombreServidor)) {
            trabajosProcesadosTotalesPorServidor.put(nombreServidor, 1+trabajosProcesadosTotalesPorServidor.get(nombreServidor));
        } else {
            trabajosProcesadosTotalesPorServidor.put(nombreServidor,1);
        }
        LocalTime diferenciaHora = diferenciaEntreLocalTimes(tiempoDeEntrada,tiempoDeSalida);
        duracionesDeRespuesta.add(traducirLocalTimeADuration(diferenciaHora));
        trabajosFinalizadosTotales++;
        //tiempoDeRespuestaTotal.plus(tiempoRespuesta);
    }

    public void añadirTrabajosRestantesAlFinalizar(int trabajos){
        trabajosRestantesAlFinalizarTotales+=trabajos;
    }

    public void añadirSimulacion() {
        simulacionesEjecutadas++;
    }

    public void añadirLlegada(){
        llegadasTotales++;
    }

    public String procesarDatos() {
        String resultado = "Simulaciones ejecutadas: "+simulacionesEjecutadas+"\n";

        //Cálculo del tiempo esperado de espera de un trabajo
        Duration waitingTime = Duration.ofNanos(0);
        for (Duration duration: duracionesEnCola) waitingTime = waitingTime.plus(duration);
        waitingTime = waitingTime.dividedBy(duracionesEnCola.size());
        resultado += "(a) Tiempo esperado de espera de un trabajo cualquiera (Wq): " + waitingTime.getSeconds()/60 + " min " + waitingTime.getSeconds() % 60 + " s\n";


        //Cálculo del tiempo esperado de respuesta
        Duration responseTime = Duration.ofNanos(0);
        for (Duration duration: duracionesDeRespuesta)
            responseTime = responseTime.plus(duration);
        responseTime = responseTime.dividedBy(duracionesDeRespuesta.size());
        resultado += "(b) Tiempo esperado de respuesta (W): " + responseTime.getSeconds()/60 + " min " + responseTime.getSeconds() % 60 + " s\n";

        resultado+="(h) Trabajos procesados esperados: "+((double)trabajosFinalizadosTotales/simulacionesEjecutadas);
        for(String nombre : trabajosProcesadosTotalesPorServidor.keySet()) {
            resultado+="\n  procesados por "+nombre+": "+((double)trabajosProcesadosTotalesPorServidor.get(nombre)/simulacionesEjecutadas);
        }
        resultado+="\n(j) Trabajos restantes esperados a las 6:03pm: "+((double)trabajosRestantesAlFinalizarTotales/simulacionesEjecutadas)+"\n";


        return resultado;
    }

    private LocalTime diferenciaEntreLocalTimes(LocalTime a, LocalTime b){
        //LocalTime a = LocalTime.of(1984, 12, 16, 7, 45, 55);
        //LocalTime b = LocalTime.of(2014, 9, 10, 6, 40, 45);


        LocalTime tempDateTime = LocalTime.from( a );

        //tempDateTime.minusNanos((long)b.getNano());
        long nanos = a.until( b, ChronoUnit.NANOS);
        long seconds = nanos / 1000000000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        nanos = nanos % 1000000000;
        seconds = seconds % 60;
        LocalTime result = LocalTime.of((int)hours,(int)minutes,(int)seconds,(int)nanos);
        /*tempDateTime = tempDateTime.plusNanos( nanos );*/
/*

        tempDateTime = tempDateTime.plusSeconds( seconds );


        tempDateTime = tempDateTime.plusMinutes( minutes );

        long hours = tempDateTime.until( b, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );*/
        return  result;
    }

    private Duration traducirLocalTimeADuration(LocalTime time){
        long nanos = 0;
        long seconds = 0;
        nanos += time.getNano();
        seconds += time.getSecond();
        seconds += time.getMinute() * 60;
        seconds += time.getHour() * 60 * 60;
        return Duration.ofSeconds(seconds,nanos);
    }
}
