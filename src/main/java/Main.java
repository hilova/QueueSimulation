//package java;

import Model.SistemaColas;
import Model.Distribucion;
import Model.DistribucionGamma;
import Model.DistribucionExponencial;
import Model.DistribucionUniforme;
import Model.DistribucionPoisson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/sample.fxml"));
        primaryStage.setTitle("Queues Simulation: ");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
