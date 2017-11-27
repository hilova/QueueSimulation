package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
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
        // registrar que se corre una nueva simulacion
        estadisticas.añadirSimulacion();

        // preparar estructuras y variables
        // primero liberar los servidores que seguían ocupados
        for(Servidor s : servidoresOcupados) {
            s.terminarTrabajo();
        }
        // luego transferirlos a la lista de servidores ociosos
        servidoresOciosos.addAll(servidoresOcupados);
        servidoresOcupados.clear();
        // limpiar la cola de trabajos
        colaTrabajos.clear();
        // poner las horas
        tiempoActual = LocalTime.of(8,0);      // reloj se empieza a las 6 am
        tiempoMax = tiempoActual.plusHours(duracionHoras);  // tiempo en que termina de acuerdo al parámetro recibido

        Duration tiempoEnCola;                              // para calcular el tiempo que un trabajo lleva en la cola


        Servidor serv = null;

        // para elegir un servidor al cual asignar llegadas
        Random generadorEnteros = new Random();
        int servidorEscogido = 0;

        generadorLlegadas.generarSiguienteLlegada(tiempoActual); // generar la primera llegada

        // lógica principal de la simulación:
        // mientras no se haya terminado el tiempo
        while(tiempoActual.isBefore(tiempoMax)) {
            // buscar trabajos más antiguos que 6 minutos
            while(!colaTrabajos.isEmpty()) {
                // cola no vacía, calcular tiempo del trabajo más antiguo
                tiempoEnCola = Duration.between(colaTrabajos.peek(), tiempoActual);
                if(tiempoEnCola.toMinutes() >= minutosMaxEnCola) {
                    // excedió 6 minutos, eliminar de la cola
                    //System.out.println(tiempoActual.toString() + ": Se eliminó un trabajo de la cola! Trabajos en espera: "+colaTrabajos.size()+"***************************\n");
                    estadisticas.añadirTiempoDeEsperaEnCola(Duration.ofMinutes(tiempoMax.getMinute())); //Se agrega un nuevo tiempo a las estadísticas
                    colaTrabajos.poll();
                } else {
                    // no habrá más trabajos por eliminar
                    break;
                }
            }

            // comparar el tiempo de la siguiente llegada con el servidor con la salida más temprana
            // servidoresOcuados.peek() devuelve este servidor
            if(servidoresOcupados.isEmpty() || generadorLlegadas.getTiempoSiguienteLlegada().isBefore(servidoresOcupados.peek().getTiempoSalida())) {
                // procesar una llegada
                //System.out.println(tiempoActual.toString() + ": Entró un trabajo!");
                estadisticas.añadirLlegadaNueva(colaTrabajos.size());


                tiempoActual = generadorLlegadas.getTiempoSiguienteLlegada(); // actualizar tiempo actual
                if(servidoresOciosos.isEmpty()) {
                    // no hay servidores disponibles, añadir el trabajo a la cola
                    //System.out.println("Ningún servidor disponible, se añadió a la cola\n");

                    colaTrabajos.add(LocalTime.from(tiempoActual));

                } else {
                    // escoger un servidor ocioso al azar

                    servidorEscogido = generadorEnteros.nextInt(servidoresOciosos.size());
                    serv = servidoresOciosos.get(servidorEscogido);

                    // asignar el trabajo al servidor escogido
                    estadisticas.añadirTiempoDeEsperaEnCola(Duration.ofMinutes(0)); //Se agrega un nuevo tiempo a las estadísticas
                    serv.asignarTrabajo(tiempoActual);

                    //System.out.println("Trabajo asignado a "+ serv.getNombre() +"\n");

                    // transferirlo a la lista de servidores ocupados
                    servidoresOcupados.add(serv);
                    servidoresOciosos.remove(servidorEscogido);
                }
                generadorLlegadas.generarSiguienteLlegada(tiempoActual); // calcular el tiempo de la siguiente llegada

            } else {
                // procesar una salida en el servidor
                // registrar estadística: cual servidor terminó el trabajo TODO enviar tiempo que tomó procesar para responder (b)
                estadisticas.añadirTrabajoFinalizado(servidoresOcupados.peek().getNombre(), servidoresOcupados.peek().getTiempoDeEntradaAlSistema(),servidoresOcupados.peek().getTiempoSalida());

                tiempoActual = servidoresOcupados.peek().getTiempoSalida(); // actualizar tiempo actual

                if(colaTrabajos.isEmpty()){
                    // cola vacía, terminar el trabajo del servidor
                    servidoresOcupados.peek().terminarTrabajo();

                    //System.out.println("La cola estaba vacía, el servidor quedó ocioso\n");
                    // transferirlo a la lista de servidores ociosos
                    servidoresOciosos.add(servidoresOcupados.poll());

                } else {
                    // hay trabajos en la cola, eliminar uno y asignarlo al servidor18`887//
                    //System.out.println("El servidor fue asignado el siguiente trabajo en la cola\n");

                    estadisticas.añadirTiempoDeEsperaEnCola(Duration.ofMinutes(tiempoActual.minusMinutes(colaTrabajos.peek().getMinute()).minusHours(colaTrabajos.peek().getHour()).getMinute()) );

                    // eliminar y reinsertar el servidor para actualizar el heap
                    serv = servidoresOcupados.poll();
                    serv.asignarTrabajo(tiempoActual,colaTrabajos.poll());
                    servidoresOcupados.add(serv);
                }

            }

        }

        estadisticas.añadirTrabajosRestantesAlFinalizar(colaTrabajos.size());
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }
}
