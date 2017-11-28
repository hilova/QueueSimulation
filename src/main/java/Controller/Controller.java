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
    public ComboBox distribucionesEntradaSelector;
    public Rectangle cola;
    public Rectangle servidor0;
    public Rectangle servidor1;
    public Rectangle servidor2;
    public Rectangle servidor3;
    public TextField paramEntrada;
    public TextArea textBox;
    public MenuItem closeItem;
    public Label lblParam1;
    public Label lblParam1Serv0;
    public Label lblParam2Serv0;
    public Label lblParam1Serv1;
    public Label lblParam2Serv1;
    public Label lblParam1Serv2;
    public Label lblParam2Serv2;
    public Label lblParam1Serv3;
    public Label lblParam2Serv3;
    public TextField param1Serv0;
    public TextField param2Serv0;
    public TextField param1Serv1;
    public TextField param2Serv1;
    public TextField param1Serv2;
    public TextField param2Serv2;
    public TextField param1Serv3;
    public TextField param2Serv3;
    public TextField simulationsQuantity;
    public TextField maxInQueue;
    public TextField simulationsDuration;



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

    public void setData(){

        distribucionesEntradaSelector.getItems().clear();

        distribucionesEntradaSelector.getItems().addAll(
                "Distribución Exponencial",
                "Distribución Poisson");

        distribucionServidor0Selector.getItems().clear();

        distribucionServidor0Selector.getItems().addAll(
                "Distribución Gamma",
                "Distribución Exponencial",
                "Distribución Uniforme");

        distribucionServidor1Selector.getItems().clear();

        distribucionServidor1Selector.getItems().addAll(
                "Distribución Gamma",
                "Distribución Exponencial",
                "Distribución Uniforme");

        distribucionServidor2Selector.getItems().clear();

        distribucionServidor2Selector.getItems().addAll(
                "Distribución Gamma",
                "Distribución Exponencial",
                "Distribución Uniforme");

        distribucionServidor3Selector.getItems().clear();

        distribucionServidor3Selector.getItems().addAll(
                "Distribución Gamma",
                "Distribución Exponencial",
                "Distribución Uniforme");
    }


    public void ingresoAlServidor(int numServidor){
    }

    public void salidaDelServidor(int numServidor){
        switch (numServidor){
            case 0:
                Thread indicador0Thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        indicadorSalida0.setVisible(true);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        indicadorSalida0.setVisible(false);
                    }
                });
                indicador0Thread.start();
                break;
            case 1:
                Thread indicador1Thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        indicadorSalida1.setVisible(true);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        indicadorSalida1.setVisible(false);
                    }
                });
                indicador1Thread.start();
                break;
            case 2:
                Thread indicador2Thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        indicadorSalida2.setVisible(true);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        indicadorSalida2.setVisible(false);
                    }
                });
                indicador2Thread.start();
                break;
            case 3:
                Thread indicador3Thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        indicadorSalida3.setVisible(true);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        indicadorSalida3.setVisible(false);
                    }
                });
                indicador3Thread.start();
                break;
            default:

        }
    }

    public void setEstadoDelServidor(int numServidor, boolean estado){
        if (estado){
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
        } else {
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
    }

    public void setTrabajosEnCola(int tamanoCola){

    }

    public void iniciarSimulacion(ActionEvent actionEvent) {
        /*    ingresoAlSistema();
            ingresoAlServidor(1);*/


        Thread ejecucionNormal = new Thread(new Runnable() {
            @Override
            public void run() {
                Distribucion[] distsServidores = new Distribucion[4];
                boolean cancelRequest = false;

                if (distribucionServidor0Selector.getValue() != null)
                    distsServidores[0] = getSeletedDistribution(distribucionServidor0Selector,Double.parseDouble(param1Serv0.getText()) ,Double.parseDouble(param2Serv0.getText()));
                else{
                    distribucionServidor0Selector.requestFocus();
                    cancelRequest = true;
                }

                if (distribucionServidor1Selector.getValue() != null)
                    distsServidores[1] = getSeletedDistribution(distribucionServidor1Selector,Double.parseDouble(param1Serv1.getText()),Double.parseDouble(param2Serv1.getText()));
                else{
                    distribucionServidor0Selector.requestFocus();
                    cancelRequest = true;
                }

                if (distribucionServidor2Selector.getValue() != null)
                    distsServidores[2] = getSeletedDistribution(distribucionServidor2Selector,Double.parseDouble(param1Serv2.getText()),Double.parseDouble(param2Serv2.getText()));
                else{
                    distribucionServidor0Selector.requestFocus();
                    cancelRequest = true;
                }

                if (distribucionServidor3Selector.getValue() != null)
                    distsServidores[3] = getSeletedDistribution(distribucionServidor3Selector,Double.parseDouble(param1Serv3.getText()),Double.parseDouble(param2Serv3.getText()));
                else{
                    distribucionServidor0Selector.requestFocus();
                    cancelRequest = true;
                }
                Distribucion distLlegadas;
                if (distribucionesEntradaSelector.getValue() != null)
                    distLlegadas = getSeletedDistribution(distribucionesEntradaSelector, Double.parseDouble(paramEntrada.getText()));
                else{
                    distLlegadas = new DistribucionPoisson(0);
                    distribucionServidor0Selector.requestFocus();
                    cancelRequest = true;
                }

                if (!cancelRequest){

                    btnSimular.setDisable(true);
                    SistemaColas sim = new SistemaColas(distsServidores, distLlegadas);


                    int cantidadDeSimulaciones = Integer.parseInt(simulationsQuantity.getText());
                    for(int i =0; i < cantidadDeSimulaciones; i++) {
                        sim.iniciarSimulacion(Integer.parseInt(simulationsDuration.getText()), Integer.parseInt(maxInQueue.getText()));
                    }

                    showResultado(sim.getEstadisticas().procesarDatos());
                    btnSimular.setDisable(false);
                }

            }
        });
        ejecucionNormal.start();
    }

    public void showResultado(String resultado){
        textBox.setText(resultado);
    }

    private Distribucion getSeletedDistribution(ComboBox comboBox, Double param1){
        Distribucion result;
        switch ((String)comboBox.getValue()){
            case "Distribución Exponencial":
                result = new DistribucionExponencial(param1);
                break;
            case "Distribución Poisson":
                result = new DistribucionPoisson(param1);
                break;
            default:
                result = new DistribucionExponencial(2);
        }
        return result;
    }

    private Distribucion getSeletedDistribution(ComboBox comboBox, Double param1, Double param2){
        Distribucion result;
        switch ((String)comboBox.getValue()){
            case "Distribución Exponencial":
                result = new DistribucionExponencial(param2);
                break;
            case "Distribución Poisson":
                result = new DistribucionPoisson(param2);
                break;
            case "Distribución Gamma":
                result = new DistribucionGamma(param1,param2);
                break;
            case "Distribución Uniforme":
                result = new DistribucionUniforme(param1,param2);
                break;
            default:
                result = new DistribucionExponencial(2);
        }
        return result;
    }

    public void changeParamBoxesEntrada(ActionEvent actionEvent){
        switch ((String)distribucionesEntradaSelector.getValue()){
            case "Distribución Exponencial":
                lblParam1.setVisible(true);
                lblParam1.setText("Mu");
                paramEntrada.setVisible(true);
                break;
            case "Distribución Poisson":
                lblParam1.setVisible(true);
                lblParam1.setText("Lambda");
                paramEntrada.setVisible(true);
                break;

            default:
                lblParam1.setVisible(false);
                paramEntrada.setVisible(false);

        }
    }


    public void changeParamBoxesServidor0(ActionEvent actionEvent){
        switch ((String)distribucionServidor0Selector.getValue()){
            case "Distribución Exponencial":
                lblParam1Serv0.setVisible(false);
                param1Serv0.setVisible(false);

                lblParam2Serv0.setVisible(true);
                lblParam2Serv0.setText("Mu");
                param2Serv0.setVisible(true);
                break;
            case "Distribución Poisson":
                lblParam1Serv0.setVisible(false);
                param1Serv0.setVisible(false);

                lblParam2Serv0.setVisible(true);
                lblParam2Serv0.setText("Lambda");
                param2Serv0.setVisible(true);
                break;
            case "Distribución Gamma":
                lblParam1Serv0.setVisible(true);
                lblParam1Serv0.setText("Alpha");
                param1Serv0.setVisible(true);

                lblParam2Serv0.setVisible(true);
                lblParam2Serv0.setText("Mu");
                param2Serv0.setVisible(true);
                break;
            case "Distribución Uniforme":
                lblParam1Serv0.setVisible(true);
                lblParam1Serv0.setText("a");
                param1Serv0.setVisible(true);

                lblParam2Serv0.setVisible(true);
                lblParam2Serv0.setText("b");
                param2Serv0.setVisible(true);
                break;
            default:
                lblParam1Serv0.setVisible(false);
                param1Serv0.setVisible(false);

                lblParam2Serv0.setVisible(false);
                param2Serv0.setVisible(false);
        }
    }

    public void changeParamBoxesServidor1(ActionEvent actionEvent){
        switch ((String)distribucionServidor1Selector.getValue()){
            case "Distribución Exponencial":
                lblParam1Serv1.setVisible(false);
                param1Serv1.setVisible(false);

                lblParam2Serv1.setVisible(true);
                lblParam2Serv1.setText("Mu");
                param2Serv1.setVisible(true);
                break;
            case "Distribución Poisson":
                lblParam1Serv1.setVisible(false);
                param1Serv1.setVisible(false);

                lblParam2Serv1.setVisible(true);
                lblParam2Serv1.setText("Lambda");
                param2Serv1.setVisible(true);
                break;
            case "Distribución Gamma":
                lblParam1Serv1.setVisible(true);
                lblParam1Serv1.setText("Alpha");
                param1Serv1.setVisible(true);

                lblParam2Serv1.setVisible(true);
                lblParam2Serv1.setText("Mu");
                param2Serv1.setVisible(true);
                break;
            case "Distribución Uniforme":
                lblParam1Serv1.setVisible(true);
                lblParam1Serv1.setText("a");
                param1Serv1.setVisible(true);

                lblParam2Serv1.setVisible(true);
                lblParam2Serv1.setText("b");
                param2Serv1.setVisible(true);
                break;
            default:
                lblParam1Serv1.setVisible(false);
                param1Serv1.setVisible(false);

                lblParam2Serv1.setVisible(false);
                param2Serv1.setVisible(false);
        }
    }

    public void changeParamBoxesServidor2(ActionEvent actionEvent){
        switch ((String)distribucionServidor2Selector.getValue()){
            case "Distribución Exponencial":
                lblParam1Serv2.setVisible(false);
                param1Serv2.setVisible(false);

                lblParam2Serv2.setVisible(true);
                lblParam2Serv2.setText("Mu");
                param2Serv2.setVisible(true);
                break;
            case "Distribución Poisson":
                lblParam1Serv2.setVisible(false);
                param1Serv2.setVisible(false);

                lblParam2Serv2.setVisible(true);
                lblParam2Serv2.setText("Lambda");
                param2Serv2.setVisible(true);
                break;
            case "Distribución Gamma":
                lblParam1Serv2.setVisible(true);
                lblParam1Serv2.setText("Alpha");
                param1Serv2.setVisible(true);

                lblParam2Serv2.setVisible(true);
                lblParam2Serv2.setText("Mu");
                param2Serv2.setVisible(true);
                break;
            case "Distribución Uniforme":
                lblParam1Serv2.setVisible(true);
                lblParam1Serv2.setText("a");
                param1Serv2.setVisible(true);

                lblParam2Serv2.setVisible(true);
                lblParam2Serv2.setText("b");
                param2Serv2.setVisible(true);
                break;
            default:
                lblParam1Serv2.setVisible(false);
                param1Serv2.setVisible(false);

                lblParam2Serv2.setVisible(false);
                param2Serv2.setVisible(false);
        }
    }

    public void changeParamBoxesServidor3(ActionEvent actionEvent){
        switch ((String)distribucionServidor3Selector.getValue()){
            case "Distribución Exponencial":
                lblParam1Serv3.setVisible(false);
                param1Serv3.setVisible(false);

                lblParam2Serv3.setVisible(true);
                lblParam2Serv3.setText("Mu");
                param2Serv3.setVisible(true);
                break;
            case "Distribución Poisson":
                lblParam1Serv3.setVisible(false);
                param1Serv3.setVisible(false);

                lblParam2Serv3.setVisible(true);
                lblParam2Serv3.setText("Lambda");
                param2Serv3.setVisible(true);
                break;
            case "Distribución Gamma":
                lblParam1Serv3.setVisible(true);
                lblParam1Serv3.setText("Alpha");
                param1Serv3.setVisible(true);

                lblParam2Serv3.setVisible(true);
                lblParam2Serv3.setText("Mu");
                param2Serv3.setVisible(true);
                break;
            case "Distribución Uniforme":
                lblParam1Serv3.setVisible(true);
                lblParam1Serv3.setText("a");
                param1Serv3.setVisible(true);

                lblParam2Serv3.setVisible(true);
                lblParam2Serv3.setText("b");
                param2Serv3.setVisible(true);
                break;
            default:
                lblParam1Serv3.setVisible(false);
                param1Serv3.setVisible(false);

                lblParam2Serv3.setVisible(false);
                param2Serv3.setVisible(false);
        }
    }
}

