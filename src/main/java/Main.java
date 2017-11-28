package main.java;

import Model.SistemaColas;
import Model.Distribucion;
import Model.DistribucionGamma;
import Model.DistribucionExponencial;
import Model.DistribucionUniforme;
import Model.DistribucionPoisson;


public class Main /*extends Application*/ {

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/


    public static void main(String[] args) {

        /*launch(args);*/



        Distribucion[] distsServidores = new Distribucion[4];
        distsServidores[0] = new DistribucionGamma(7,3);
        distsServidores[1] = new DistribucionGamma(5,2);
        distsServidores[2] = new DistribucionExponencial(0.3);
        distsServidores[3] = new DistribucionUniforme(4,9);

        Distribucion distLlegadas = new DistribucionExponencial(2);

        SistemaColas sim = new SistemaColas(distsServidores, distLlegadas);

        for(int i =0; i<10000; i++) {
            sim.iniciarSimulacion(10, 6);
        }

        System.out.println(sim.getEstadisticas().procesarDatos());
    }
}
