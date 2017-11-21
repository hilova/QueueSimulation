package sample.Model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

public class SistemaColas {
    private LinkedBlockingQueue<LocalTime> colaTrabajos; // se guardan los tiempos de llegada de cada trabajo para revisar si han esperado por 2 minutos
    private LinkedList<Servidor> servidoresOciosos;
    private PriorityQueue<Servidor> servidoresOcupados; // guarda los servidores en orden de acuerd a sus tiempos de salida
    private LocalTime tiempoActual, tiempoMax;
    private GeneradorDeTrabajos generadorLlegadas;

    public SistemaColas() {
        colaTrabajos = new LinkedBlockingQueue<LocalTime>();
        servidoresOciosos = new LinkedList<Servidor>();
        servidoresOcupados = new PriorityQueue<Servidor>();
        generadorLlegadas = null;
        tiempoActual = null;
        tiempoMax = null;
    }

    public void iniciarSimulacion(int duracionHoras, Distribucion[] distsServidores, Distribucion distLlegadas) {

        tiempoActual = LocalTime.of(5,0);                   // reloj se empieza a las 6 am
        tiempoMax = tiempoActual.plusHours(duracionHoras);  // tiempo en que termina de acuerdo al parámetro recibido

        Duration tiempoEnCola;                              // para calcular el tiempo que un trabajo lleva en la cola

        Servidor serv = null;

        // para elegir un servidor al cual asignar llegadas
        Random generadorEnteros = new Random();
        int servidorEscogido = 0;

        // inicializar los servidores y almacenarlos en la lista de ociosos
        for(int i = 0; i < distsServidores.length; i++) {
            servidoresOciosos.add(new Servidor(distsServidores[i]));
        }

        // inicializar el generador de llegadas con la distribución recibida
        generadorLlegadas = new GeneradorDeTrabajos(distLlegadas);
        generadorLlegadas.generarSiguienteLlegada(tiempoActual); // generar la primera llegada

        // lógica principal de la simulación:
        // mientras no se haya terminado el tiempo
        while(tiempoActual.isBefore(tiempoMax)) {

            // TODO debería ser un loop que se fija en cada trabajo, hasta sacar todos o encontrar que uno no se debe sacar, ya que podría haber más de un trabajo de mas de 6 mins
            // checkear si el trabajo más antiguo tiene más de 6 minutos, por lo que debe ser eliminado
            if(!colaTrabajos.isEmpty()) {
                // cola no vacía, calcular tiempo del trabajo más antiguo
                tiempoEnCola = Duration.between(colaTrabajos.peek(), tiempoActual);
                if(tiempoEnCola.toMinutes() >= 6) {
                    // excedió 6 minutos, eliminar de la cola
                    colaTrabajos.poll();
                }
            }

            // comparar el tiempo de la sigueinte llegada con el servidor con la salida más temprana
            // servidoresOcuados.peek() devuelve este servidor
            if(servidoresOcupados.isEmpty() || generadorLlegadas.getTiempoSiguienteLlegada().isBefore(servidoresOcupados.peek().getTiempoSalida())) {
                // procesar una llegada
                System.out.println(tiempoActual.toString() + ": entró un trabajo!");

                tiempoActual = generadorLlegadas.getTiempoSiguienteLlegada(); // actualizar tiempo actual
                if(servidoresOciosos.isEmpty()) {
                    // no hay servidores disponibles, añadir el trabajo a la cola
                    System.out.println("ningún servidor disponible, se añadió a la cola\n");

                    colaTrabajos.add(LocalTime.from(tiempoActual));

                } else {
                    // escoger un servidor ocioso al azar
                    System.out.println("trabajo asignado a un servidor\n");

                    servidorEscogido = generadorEnteros.nextInt(servidoresOciosos.size());
                    serv = servidoresOciosos.get(servidorEscogido);

                    // asignar el trabajo al servidor escogido
                    serv.asignarTrabajo(tiempoActual);

                    // transferirlo a la lista de servidores ocupados
                    servidoresOcupados.add(serv);
                    servidoresOciosos.remove(servidorEscogido);
                }
                generadorLlegadas.generarSiguienteLlegada(tiempoActual); // calcular el tiempo de la siguiente llegada

            } else {
                // procesar una salida en el servidor
                System.out.println(tiempoActual.toString() + ": un servidor terminó un trabajo!");

                tiempoActual = servidoresOcupados.peek().getTiempoSalida(); // actualizar tiempo actual

                if(colaTrabajos.isEmpty()){
                    // cola vacía, terminar el trabajo del servidor
                    System.out.println("la cola estaba vacía, el servidor queda ocioso\n");

                    servidoresOcupados.peek().terminarTrabajo();
                    // transferirlo a la lista de servidores ociosos
                    servidoresOciosos.add(servidoresOcupados.poll());

                } else {
                    // hay trabajos en la cola, eliminar uno y asignarlo al servidor
                    System.out.println("el servidor fue asignado el siguiente trabajo en la cola\n");
                    colaTrabajos.poll();

                    // eliminar y reinsertar el servidor para actualizar el heap
                    serv = servidoresOcupados.poll();
                    serv.asignarTrabajo(tiempoActual);
                    servidoresOcupados.add(serv);

                }

            }

        }

    }
}
