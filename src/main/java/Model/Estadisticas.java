package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class Estadisticas {
    // SE REGISTRAN LOS TOTALES PARA CALCULAR LOS DATOS AL FINALIZAR
    // (a) the expected waiting time for a randomly selected job
    private Duration tiempoEsperandoEnColaTotal;

    // (b) the expected response time;
    private Duration tiempoDeRespuestaTotal;

    // (c) the expected length of a queue (excluding the jobs receiving service), when a new job arrives
    private int longitudDeColaPorLlegadaTotal;

    // (d) the expected maximum waiting time during a 10-hour day
    private Duration tiempoDeEsperaMaximoEnUnDia;   // reinicia con cada simulacion
    private Duration tiempoDeEsperaMaximoTotal;     // promedio de todas las simulaciones

    // (e) the expected maximum length of a queue during a 10-hour day
    private int longitudDeColaMaximaEnUnDia;
    private int longitudDeColaMaximaTotal;

    // (f) the probability that at least one server is available, when a job arrives
    private int vecesUnServidorDisponibleTotales;

    // (g) the probability that at least two servers are available, when a job arrives
    private int vecesDosServidoresDisponiblesTotales;

    // (h) the expected number of jobs processed by each server
    private HashMap<String, Integer> trabajosProcesadosTotalesPorServidor = new HashMap<>();

    // (i) the expected time each server is idle during the day
    private HashMap<String, Duration> tiemposOciosoTotalesPorServidor = new HashMap<>();

    // (j) the expected number of jobs still remaining in the system at 6:03 pm
    private double trabajosRestantesAlFinalizarTotales;

    // (k) the expected percentage of jobs that left the queue prematurely
    private double trabajosAbandonaronColaTotales;

    // para calcular (d), (e), (h), (i), (j)
    private int simulacionesEjecutadas;

    // para calcular (a), (c), (f), (g), (k)
    private int llegadasTotales;

    // para calcular (b)
    private int trabajosFinalizadosTotales;

    public Estadisticas() {
        tiempoEsperandoEnColaTotal = Duration.ZERO;
        tiempoDeRespuestaTotal = Duration.ZERO;
        longitudDeColaPorLlegadaTotal = 0;
        tiempoDeEsperaMaximoEnUnDia = Duration.ZERO;
        tiempoDeEsperaMaximoTotal = Duration.ZERO;
        longitudDeColaMaximaEnUnDia = 0;
        longitudDeColaMaximaTotal = 0;
        vecesUnServidorDisponibleTotales = 0;
        vecesDosServidoresDisponiblesTotales = 0;
        trabajosProcesadosTotalesPorServidor = new HashMap<>();
        tiemposOciosoTotalesPorServidor = new HashMap<>();
        trabajosRestantesAlFinalizarTotales = 0;
        trabajosAbandonaronColaTotales = 0;
        simulacionesEjecutadas = 0;
        llegadasTotales = 0;
        trabajosFinalizadosTotales = 0;
    }

    //Registra una llegada nueva
    public void añadirLlegadaNueva(int longitudDeCola, int servidoresDisponibles) {
        // añadir la lllegada
        llegadasTotales++;

        // añadir la longitud a la longitud total
        longitudDeColaPorLlegadaTotal += longitudDeCola;

        // revisar si es la longitud máxima
        if(longitudDeColaMaximaEnUnDia == 0 || longitudDeCola > longitudDeColaMaximaEnUnDia) {
            longitudDeColaMaximaEnUnDia = longitudDeCola;
        }

        // registrar si hubo al menos 1 servidor disponible
        if(servidoresDisponibles >= 1) {
            vecesUnServidorDisponibleTotales++;
        }

        // registrar si hubieron al menos 2 servidores disponibles
        if(servidoresDisponibles >= 2) {
            vecesDosServidoresDisponiblesTotales++;
        }
    }

    //Registra un tiempo de espera en la cola. Nota: Si no pasó por cola el tiempo es 0
    public void añadirTiempoDeEsperaEnCola(Duration esperaEnCola) {
        // sumar el tiempo esperado al tiempo esperado total
        tiempoEsperandoEnColaTotal = tiempoEsperandoEnColaTotal.plus(esperaEnCola);

        // revisar si es el tiempo de espera máximo
        if(tiempoDeEsperaMaximoEnUnDia.equals(Duration.ZERO) || esperaEnCola.compareTo(tiempoDeEsperaMaximoEnUnDia)>0 ){
            tiempoDeEsperaMaximoEnUnDia = Duration.from(esperaEnCola);
        }
    }

    public void añadirTrabajoAbandonoLaCola() {
        trabajosAbandonaronColaTotales++;
    }

    public void añadirTrabajoFinalizado(String nombreServidor, LocalTime tiempoDeEntrada, LocalTime tiempoDeSalida){
        // añadir un trabajo finalizado
        trabajosFinalizadosTotales++;

        // registrar el servidor que lo completó
        if (trabajosProcesadosTotalesPorServidor.containsKey(nombreServidor)) {
            // sumar al valor existente
            trabajosProcesadosTotalesPorServidor.put(nombreServidor, 1+trabajosProcesadosTotalesPorServidor.get(nombreServidor));
        } else {
            // insertar por primera vez
            trabajosProcesadosTotalesPorServidor.put(nombreServidor,1);
        }

        // calcular la diferencia de localTimes para encontrar el tiempo de respuesta, llamar abs() para garantizar que sea un tiempo positivo
        Duration tiempoDeRespuesta = Duration.between(tiempoDeEntrada,tiempoDeSalida).abs();

        // añadirlo al tiempo de respuesta total
        tiempoDeRespuestaTotal = tiempoDeRespuestaTotal.plus(tiempoDeRespuesta);
    }

    // Registra los trabajos restantes y actualiza las variables de máximos
    public void terminarSimulacion(int trabajosRestantes) {
        // añadir una simulacion ejecutada
        simulacionesEjecutadas++;

        // añadir los trabajos restantes sin procesar
        trabajosRestantesAlFinalizarTotales += trabajosRestantes;

        // procesar el tiempo de espera máximo en ese día
        tiempoDeEsperaMaximoTotal = tiempoDeEsperaMaximoTotal.plus(tiempoDeEsperaMaximoEnUnDia);
        tiempoDeEsperaMaximoEnUnDia = Duration.ZERO;                // reiniciar el tiempo máximo en un día para la siguiente simulación

        // procesar la longitud de cola máxima en ese día
        longitudDeColaMaximaTotal += longitudDeColaMaximaEnUnDia;   // reiniciar la longitd maxima en u dia para la siguiente simulacion
        longitudDeColaMaximaEnUnDia = 0;
    }

    // Añade tiempo ocioso a un servidor cuando se le asigna un trabajo al haber estado ocioso
    public void añadirTiempoOciosoAServidor(String nombre, LocalTime tiempoInicio, LocalTime tiempoFin) {
        if(tiemposOciosoTotalesPorServidor.containsKey(nombre)) {
            // sumar al valor existente
            tiemposOciosoTotalesPorServidor.put(nombre, tiemposOciosoTotalesPorServidor.get(nombre).plus(Duration.between(tiempoInicio,tiempoFin).abs()));
        } else {
            // insertar por primera vez
            tiemposOciosoTotalesPorServidor.put(nombre, Duration.between(tiempoInicio,tiempoFin).abs());
        }
    }

    // redondear doubles a dos decimales
    public double redondear(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String procesarDatos() {
        String resultado = "Simulaciones ejecutadas: " + simulacionesEjecutadas + "\n";

        // Cálculo del tiempo esperado de espera de un trabajo
        Duration tiempoEsperandoEnColaEsperado = tiempoEsperandoEnColaTotal.dividedBy(llegadasTotales);
        resultado += "(a) Tiempo esperado de espera de un trabajo cualquiera (Wq): " + tiempoEsperandoEnColaEsperado.getSeconds()/60 + " min " + tiempoEsperandoEnColaEsperado.getSeconds() % 60 + " s\n";

        // Cálculo del tiempo esperado de respuesta
        Duration tiempoDeRespuestaEsperado = tiempoDeRespuestaTotal.dividedBy(trabajosFinalizadosTotales);
        resultado += "(b) Tiempo esperado de respuesta (W): " + tiempoDeRespuestaEsperado.getSeconds()/60 + " min " + tiempoDeRespuestaEsperado.getSeconds() % 60 + " s\n";

        // Cálculo de la longitud esperada de la cola al darse una llegada
        double longitudDeColaPorLlegadaEsperada = (double)longitudDeColaPorLlegadaTotal/llegadasTotales;
        resultado += "(c) Longitud esperada de la cola cuando se da una llegada (Lq): " + redondear(longitudDeColaPorLlegadaEsperada) + " trabajos\n";

        // Tiempo máximo de espera esperado
        Duration tiempoDeEsperaMaximoEsperado = tiempoDeEsperaMaximoTotal.dividedBy(simulacionesEjecutadas);
        resultado += "(d) Tiempo de espera máximo esperado: "+ tiempoDeEsperaMaximoEsperado.getSeconds()/60 + " min " + tiempoDeEsperaMaximoEsperado.getSeconds() % 60 + " s\n";

        // Longitud máxima esperada en la cola
        double longitudDeColaMaximaEsperada = (double) longitudDeColaMaximaTotal/simulacionesEjecutadas;
        resultado += "(e) Longitud máxima esperada en la cola: " + redondear(longitudDeColaMaximaEsperada) +" trabajos \n";

        // Probabilidad de que hubiera al menos 1 servidor disponible al entrar un trabajo
        double probabilidadUnServidorDisponible = (double) vecesUnServidorDisponibleTotales/llegadasTotales * 100;
        resultado += "(f) Probabilidad de que haya al menos 1 servidor disponible al entrar un trabajo "+redondear(probabilidadUnServidorDisponible)+"%\n";

        // Probabilidad de que hubieran al menos 2 servidores disponibles al entrar un trabajo
        double probabilidadDosServidoresDisponibles = (double) vecesDosServidoresDisponiblesTotales/llegadasTotales * 100;
        resultado += "(g) Probabilidad de que haya al menos 2 servidores disponibles al entrar un trabajo "+redondear(probabilidadDosServidoresDisponibles)+"%\n";

        // Trabajos procesados totales y por cada servidor
        resultado+="(h) Trabajos procesados esperados: "+(redondear((double)trabajosFinalizadosTotales/simulacionesEjecutadas));
        for(String nombre : trabajosProcesadosTotalesPorServidor.keySet()) {
            resultado+="\n       -"+nombre+": "+(redondear((double)trabajosProcesadosTotalesPorServidor.get(nombre)/simulacionesEjecutadas))+" trabajos";
        }

        // Tiempo esperado ocioso de cada servidor
        resultado+="\n(i) Tiempo ocioso esperado de cada servidor:";
        Duration tiempoOciosoEsperadoPorServidor;
        for(String nombre : tiemposOciosoTotalesPorServidor.keySet()) {
            tiempoOciosoEsperadoPorServidor = tiemposOciosoTotalesPorServidor.get(nombre).dividedBy(simulacionesEjecutadas);
            resultado+="\n       -"+nombre+": "+tiempoOciosoEsperadoPorServidor.getSeconds()/60+" min " + tiempoOciosoEsperadoPorServidor.getSeconds()%60 + " s";
        }

        // Trabajos no procesados a las 6:03pm
        resultado+="\n(j) Trabajos restantes esperados a las 6:03pm: "+((double)trabajosRestantesAlFinalizarTotales/simulacionesEjecutadas)+"\n";

        // Trabajos esperados que abandonan la cola
        double trabajosAbandonaronColaEsperados = (double) trabajosAbandonaronColaTotales/llegadasTotales * 100;
        resultado += "(k) Porcentaje esperado de trabajos que abandonaron la cola prematuramente "+redondear(trabajosAbandonaronColaEsperados)+"%\n";

        return resultado;
    }
}
