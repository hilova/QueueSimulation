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
    private QueueLogger bitacora;

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
        this.bitacora = new QueueLogger();

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

    public QueueLogger getBitacora() {
        return bitacora;
    }

    public void iniciarSimulacion(int duracionHoras, int minutosMaxEnCola) {


        // preparar estructuras y variables
        // poner las horas
        tiempoActual = LocalTime.of(8,0);      // reloj se empieza a las 6 am
        tiempoMax = tiempoActual.plusHours(duracionHoras);  // tiempo en que termina de acuerdo al parámetro recibido
        // transferir todos los servidores a la lista de servidores ociosos
        servidoresOciosos.addAll(servidoresOcupados);
        servidoresOcupados.clear();
        // liberar los servidores que seguían ocupados
        for(Servidor s : servidoresOciosos) {

            s.terminarTrabajo(tiempoActual);
        }
        // limpiar la cola de trabajos
        colaTrabajos.clear();

        Duration duracionMaxEnCola = Duration.ofMinutes(minutosMaxEnCola);

        Duration tiempoEnCola;                              // para calcular el tiempo que un trabajo lleva en la cola


        Servidor serv = null;

        // para elegir un servidor al cual asignar llegadas
        Random generadorEnteros = new Random();
        int servidorEscogido = 0;

        generadorLlegadas.generarSiguienteLlegada(tiempoActual); // generar la primera llegada

        //Se registra en la bitacora lo sucedido.
        bitacora.register(0, "-----------------------------------------------------------------SIMULACIÓN INICIADA");

        // lógica principal de la simulación:
        // mientras no se haya terminado el tiempo
        while(tiempoActual.isBefore(tiempoMax)) {

            // comparar el tiempo de la siguiente llegada con el servidor con la salida más temprana
            // servidoresOcuados.peek() devuelve este servidor
            if(servidoresOcupados.isEmpty() || generadorLlegadas.getTiempoSiguienteLlegada().isBefore(servidoresOcupados.peek().getTiempoSalida())) {
                // procesar una llegada
                estadisticas.añadirLlegadaNueva(colaTrabajos.size(), servidoresOciosos.size());
                bitacora.register(1, "Ha llegado un trabajo al sistema.");


                tiempoActual = generadorLlegadas.getTiempoSiguienteLlegada(); // actualizar tiempo actual
                if(servidoresOciosos.isEmpty()) {
                    // no hay servidores disponibles, añadir el trabajo a la cola
                    colaTrabajos.add(LocalTime.from(tiempoActual));
                    //Se registra en la bitacora lo sucedido.
                    bitacora.register(2, "Ha entrado un trabajo a la cola.");
                    bitacora.register(7, "Longitud de cola actual: " + colaTrabajos.size());

                } else {
                    // escoger un servidor ocioso al azar

                    servidorEscogido = generadorEnteros.nextInt(servidoresOciosos.size());
                    serv = servidoresOciosos.get(servidorEscogido);

                    // asignar el trabajo al servidor escogido
                    estadisticas.añadirTiempoOciosoAServidor(serv.getNombre(), serv.getTiempoDeInicioDeOcio(), tiempoActual);  // Se agrega el tiempo ocioso del servidor
                    serv.asignarTrabajo(tiempoActual);
                    //Se registra en la bitacora lo sucedido.
                    bitacora.register(30+serv.getId(), "Un trabajo ha sido asignado al servidor " + serv.getId());

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
                //Se registra en la bitacora lo sucedido.
                bitacora.register(40+servidoresOcupados.peek().getId(), "Ha finalizado un trabajo en el servidor " + servidoresOcupados.peek().getId());


                // actualizar la cola eliminando trabajos que hayan excedido el tiempo límite
                while(!colaTrabajos.isEmpty()) {
                    // cola no vacía, calcular tiempo del trabajo más antiguo
                    tiempoEnCola = Duration.between(colaTrabajos.peek(), tiempoActual).abs();
                    if(tiempoEnCola.compareTo(duracionMaxEnCola) >= 0) {
                        // excedió el tiempo maximo, eliminar de la cola
                        estadisticas.añadirTiempoDeEsperaEnCola(duracionMaxEnCola); // El trabajo fue eliminado, se agrega el tiempo maximo a la estadisticas)
                        estadisticas.añadirTrabajoAbandonoLaCola();                 // Se registra que el trabajo abandonó la cola
                        bitacora.register(5, "Trabajo sacado de cola por exceder tiempo límite.");  //Se registra en la bitacora lo sucedido.
                        bitacora.register(7, "Longitud de cola actual: " + colaTrabajos.size());

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
                    bitacora.register(7, "Longitud de cola actual: " + colaTrabajos.size());

                } else {
                    // hay trabajos en la cola, eliminar uno y asignarlo al servidor

                    // registrar el tiempo de espera del trabajo
                    estadisticas.añadirTiempoDeEsperaEnCola(Duration.between(colaTrabajos.peek(), tiempoActual).abs());

                    // eliminar y reinsertar el servidor para actualizar el heap
                    serv = servidoresOcupados.poll();

                    //Se registra en la bitacora lo sucedido.
                    bitacora.register(30+serv.getId(), "Un trabajo ha sido asignado al servidor " + serv.getId());
                    bitacora.register(7, "Longitud de cola actual: " + colaTrabajos.size());

                    serv.asignarTrabajo(tiempoActual,colaTrabajos.poll());
                    servidoresOcupados.add(serv);
                }

            }

        }

        estadisticas.terminarSimulacion(colaTrabajos.size());
        //Se registra en la bitacora lo sucedido.
        bitacora.register(6, "-----------------------------------------------------------------SIMULACIÓN TERMINADA");
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }
}
