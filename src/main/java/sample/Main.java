package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.SistemaColas;
import sample.Model.DistribucionPoisson;
import sample.Model.DistribucionGamma;
import sample.Model.DistribucionExponencial;
import sample.Model.DistribucionUniforme;
import sample.Model.Distribucion;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }


    public static void main(String[] args) {

        //launch(args);

        SistemaColas sim = new SistemaColas();

        Distribucion[] distsServidores = new Distribucion[4];
        distsServidores[0] = new DistribucionGamma(7,3);
        distsServidores[1] = new DistribucionGamma(5,2);
        distsServidores[2] = new DistribucionExponencial(0.3);
        distsServidores[3] = new DistribucionUniforme(4,9);

        Distribucion distLlegadas = new DistribucionPoisson(2);

        sim.iniciarSimulacion(10, distsServidores, distLlegadas);
    }
}
