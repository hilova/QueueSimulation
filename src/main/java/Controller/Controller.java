package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

public class Controller {


    public Button btnSimular;
    public SVGPath indicadorEntrada;
    public SVGPath indicadorSalida0;
    public SVGPath indicadorSalida1;
    public SVGPath indicadorSalida2;
    public SVGPath indicadorSalida3;
    public ComboBox distribucionServidor0Selector;
    public ComboBox distribucionServidor1Selector;
    public ComboBox distribucionServidor2Selector;
    public ComboBox distribucionServidor3Selector;
    public Rectangle cola;
    public Rectangle servidor0;
    public Rectangle servidor1;
    public Rectangle servidor2;
    public Rectangle servidor3;
    public TextField muEntrada;
    public TextArea textBox;
    public MenuItem closeItem;



    public void ingresoAlSistema() {
        Thread hola = new Thread(new Runnable() {
            @Override
            public void run() {
                indicadorEntrada.setVisible(true);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                indicadorEntrada.setVisible(false);
            }
        });
        hola.start();

    }

    public void exit(ActionEvent actionEvent){
        Platform.exit();
    }

    public void ingresoAlServidor(int numServidor){
        switch (numServidor){
            case 0:
                servidor0.setFill(Color.valueOf("#0f5fa4"));
                break;
            case 1:
                servidor1.setFill(Color.valueOf("#0f5fa4"));
                break;
            case 2:
                servidor2.setFill(Color.valueOf("#0f5fa4"));
                break;
            case 3:
                servidor3.setFill(Color.valueOf("#0f5fa4"));
                break;
            default:

        }
    }

    public void salidaDelServidor(int numServidor){
        switch (numServidor){
            case 0:
                servidor0.setFill(Color.DODGERBLUE);
                break;
            case 1:
                servidor1.setFill(Color.DODGERBLUE);
                break;
            case 2:
                servidor2.setFill(Color.DODGERBLUE);
                break;
            case 3:
                servidor3.setFill(Color.DODGERBLUE);
                break;
            default:

        }
    }

    public void setEstadoDelServidor(int numServidor, boolean estado){

    }

    public void setTrabajosEnCola(int tamanoCola){

    }

    public void iniciarSimulacion(ActionEvent actionEvent) {
        /*    ingresoAlSistema();
            ingresoAlServidor(1);*/

        btnSimular.setDisable(true);
        Thread ejecucionNormal = new Thread(new Runnable() {
            @Override
            public void run() {
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

                showResultado(sim.getEstadisticas().procesarDatos());
                btnSimular.setDisable(false);
            }
        });
        ejecucionNormal.start();
    }

    public void showResultado(String resultado){
        textBox.setText(resultado);
    }
}
