package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;


public class SistemaColas {
    private LinkedBlockingQueue<LocalTime> colaTrabajos; // se guardan los tiempos de llegada de cada trabajo para revisar si han esperado por 2 minutos
    private LinkedList<Servidor> servidoresOciosos;
    private PriorityQueue<Servidor> servidoresOcupados; // guarda los servidores en orden de acuerdo a sus tiempos de salida
    private LocalTime tiempoActual, tiempoMax;
    private GeneradorDeTrabajos generadorLlegadas;
    private Estadisticas estadisticas;

    public SistemaColas(Distribucion[] distsServidores, Distribucion distLlegadas, Estadisticas estadisticas) {
        colaTrabajos = new LinkedBlockingQueue<LocalTime>();
        servidoresOciosos = new LinkedList<Servidor>();
        servidoresOcupados = new PriorityQueue<Servidor>();
        this.estadisticas = estadisticas;

        // inicializar los servidores y almacenarlos en la lista de ociosos
        Servidor serv;
        for(int i = 0; i < distsServidores.length; i++) {
            serv = new Servidor(distsServidores[i], "Servidor #"+i+" de distribución "+distsServidores[i].getName());
            servidoresOciosos.add(serv);
        }

        // inicializar el generador de llegadas con la distribución recibida
        generadorLlegadas = new GeneradorDeTrabajos(distLlegadas);

        tiempoActual = null;
        tiempoMax = null;
    }

    public SistemaColas(Distribucion[] distsServidores, Distribucion distLlegadas) {
        colaTrabajos = new LinkedBlockingQueue<LocalTime>();
        servidoresOciosos = new LinkedList<Servidor>();
        servidoresOcupados = new PriorityQueue<Servidor>();
        this.estadisticas = new Estadisticas();

        // inicializar los servidores y almacenarlos en la lista de ociosos
        Servidor serv;
        for(int i = 0; i < distsServidores.length; i++) {
            serv = new Servidor(distsServidores[i], "Servidor #"+i+" de distribución "+distsServidores[i].getName());
            servidoresOciosos.add(serv);
        }

        // inicializar el generador de llegadas con la distribución recibida
        generadorLlegadas = new GeneradorDeTrabajos(distLlegadas);

        tiempoActual = null;
        tiempoMax = null;
    }

    public void iniciarSimulacion(int duracionHoras,int minutosMaxEnCola) {

        // preparar estructuras y variables
        // poner las horas
        tiempoActual = LocalTime.of(8,0);      // reloj se empieza a las 6 am
        tiempoMax = tiempoActual.plusHours(duracionHoras);  // tiempo en que termina de acuerdo al parámetro recibido
        // liberar los servidores que seguían ocupados
        for(Servidor s : servidoresOcupados) {
            s.terminarTrabajo(tiempoActual);
        }
        // transferirlos a la lista de servidores ociosos
        servidoresOciosos.addAll(servidoresOcupados);
        servidoresOcupados.clear();
        // limpiar la cola de trabajos
        colaTrabajos.clear();

        Duration duracionMaxEnCola = Duration.ofMinutes(minutosMaxEnCola);

        Duration tiempoEnCola;                              // para calcular el tiempo que un trabajo lleva en la cola


        Servidor serv = null;

        // para elegir un servidor al cual asignar llegadas
        Random generadorEnteros = new Random();
        int servidorEscogido = 0;

        generadorLlegadas.generarSiguienteLlegada(tiempoActual); // generar la primera llegada

        // lógica principal de la simulación:
        // mientras no se haya terminado el tiempo
        while(tiempoActual.isBefore(tiempoMax)) {

            // comparar el tiempo de la siguiente llegada con el servidor con la salida más temprana
            // servidoresOcuados.peek() devuelve este servidor
            if(servidoresOcupados.isEmpty() || generadorLlegadas.getTiempoSiguienteLlegada().isBefore(servidoresOcupados.peek().getTiempoSalida())) {
                // procesar una llegada
                estadisticas.añadirLlegadaNueva(colaTrabajos.size(), servidoresOciosos.size());


                tiempoActual = generadorLlegadas.getTiempoSiguienteLlegada(); // actualizar tiempo actual
                if(servidoresOciosos.isEmpty()) {
                    // no hay servidores disponibles, añadir el trabajo a la cola
                    colaTrabajos.add(LocalTime.from(tiempoActual));

                } else {
                    // escoger un servidor ocioso al azar

                    servidorEscogido = generadorEnteros.nextInt(servidoresOciosos.size());
                    serv = servidoresOciosos.get(servidorEscogido);

                    // asignar el trabajo al servidor escogido
                    estadisticas.añadirTiempoOciosoAServidor(serv.getNombre(), serv.getTiempoSalida(), tiempoActual);  // Se agrega el tiempo ocioso del servidor
                    serv.asignarTrabajo(tiempoActual);

                    // transferirlo a la lista de servidores ocupados
                    servidoresOcupados.add(serv);
                    servidoresOciosos.remove(servidorEscogido);
                }
                generadorLlegadas.generarSiguienteLlegada(tiempoActual); // calcular el tiempo de la siguiente llegada

            } else {
                // procesar una salida en el servidor
                tiempoActual = servidoresOcupados.peek().getTiempoSalida(); // actualizar tiempo actual
                // registrar estadística: cual servidor terminó el trabajo
                estadisticas.añadirTrabajoFinalizado(servidoresOcupados.peek().getNombre(), servidoresOcupados.peek().getTiempoDeEntradaAlSistema(),tiempoActual);

                // actualizar la cola eliminando trabajos que hayan excedido el tiempo límite
                while(!colaTrabajos.isEmpty()) {
                    // cola no vacía, calcular tiempo del trabajo más antiguo
                    tiempoEnCola = Duration.between(colaTrabajos.peek(), tiempoActual).abs();
                    if(tiempoEnCola.compareTo(duracionMaxEnCola) >= 0) {
                        // excedió el tiempo maximo, eliminar de la cola
                        estadisticas.añadirTiempoDeEsperaEnCola(duracionMaxEnCola); // El trabajo fue eliminado, se agrega el tiempo maximo a la estadisticas)
                        estadisticas.añadirTrabajoAbandonoLaCola();                 // Se registra que el trabajo abandonó la cola
                        colaTrabajos.poll();
                    } else {
                        // no habrá más trabajos por eliminar
                        break;
                    }
                }

                if(colaTrabajos.isEmpty()){
                    // cola vacía, terminar el trabajo del servidor
                    servidoresOcupados.peek().terminarTrabajo(tiempoActual);

                    // transferirlo a la lista de servidores ociosos
                    servidoresOciosos.add(servidoresOcupados.poll());

                } else {
                    // hay trabajos en la cola, eliminar uno y asignarlo al servidor

                    // registrar el tiempo de espera del trabajo
                    estadisticas.añadirTiempoDeEsperaEnCola(Duration.between(colaTrabajos.peek(), tiempoActual).abs());

                    // eliminar y reinsertar el servidor para actualizar el heap
                    serv = servidoresOcupados.poll();
                    serv.asignarTrabajo(tiempoActual,colaTrabajos.poll());
                    servidoresOcupados.add(serv);
                }

            }

        }

        estadisticas.terminarSimulacion(colaTrabajos.size());
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }
}
