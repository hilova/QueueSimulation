package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Pair;


public class Controller {


    public Button btnSimular;
    public Button stopAnimation;
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
    public ImageView work0;
    public ImageView work1;
    public ImageView work2;
    public ImageView work3;
    public ImageView work4;
    public ImageView work5;
    public ImageView work6;
    public ImageView work7;
    public ImageView work8;
    public ImageView work9;
    public ImageView work10;
    public ImageView work11;
    public ImageView work12;

    private Thread animation;
    private volatile boolean animationContinue;


    public Controller(){
    }
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
        switch (tamanoCola){
            case 0:
                work0.setVisible(false);
                work1.setVisible(false);
                work2.setVisible(false);
                work3.setVisible(false);
                work4.setVisible(false);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 1:
                work0.setVisible(true);
                work1.setVisible(false);
                work2.setVisible(false);
                work3.setVisible(false);
                work4.setVisible(false);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 2:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(false);
                work3.setVisible(false);
                work4.setVisible(false);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 3:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(false);
                work4.setVisible(false);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 4:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(false);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 5:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(false);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 6:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(false);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 7:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(false);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 8:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(false);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 9:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(false);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 10:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(true);
                work10.setVisible(false);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 11:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(true);
                work10.setVisible(true);
                work11.setVisible(false);
                work12.setVisible(false);
                break;
            case 12:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(true);
                work10.setVisible(true);
                work11.setVisible(true);
                work12.setVisible(false);
                break;
            case 13:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(true);
                work10.setVisible(true);
                work11.setVisible(true);
                work12.setVisible(true);
                break;
            default:
                work0.setVisible(true);
                work1.setVisible(true);
                work2.setVisible(true);
                work3.setVisible(true);
                work4.setVisible(true);
                work5.setVisible(true);
                work6.setVisible(true);
                work7.setVisible(true);
                work8.setVisible(true);
                work9.setVisible(true);
                work10.setVisible(true);
                work11.setVisible(true);
                work12.setVisible(true);
                break;
        }

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

                    animation = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            animationContinue = true;
                            stopAnimation.setVisible(true);
                            for (Pair<Integer, String> linea: sim.getBitacora()){
                                if (animationContinue == false) break;
                                System.out.println(linea.getKey()+ "\t" + linea.getValue());
                                try{
                                    switch (linea.getKey()){
                                        case 1:
                                            ingresoAlSistema();
                                            break;
                                        case 2:
                                            break;
                                        case 31:
                                            setEstadoDelServidor(0,true);
                                            ingresoAlServidor(0);
                                            break;
                                        case 32:
                                            setEstadoDelServidor(1,true);
                                            ingresoAlServidor(1);
                                            break;
                                        case 33:
                                            setEstadoDelServidor(2,true);
                                            ingresoAlServidor(2);
                                            break;
                                        case 34:
                                            setEstadoDelServidor(3,true);
                                            ingresoAlServidor(3);
                                            break;
                                        case 41:
                                            setEstadoDelServidor(0,false);
                                            salidaDelServidor(0);
                                            break;
                                        case 42:
                                            setEstadoDelServidor(1,false);
                                            salidaDelServidor(1);
                                            break;
                                        case 43:
                                            setEstadoDelServidor(2,false);
                                            salidaDelServidor(2);
                                            break;
                                        case 44:
                                            setEstadoDelServidor(3,false);
                                            salidaDelServidor(3);
                                            break;
                                        case 5:
                                            break;
                                        case 6:
                                            setTrabajosEnCola(0);
                                            break;
                                        case 7:
                                            int quantity = Integer.parseInt(linea.getValue().split(": ")[1]);
                                            setTrabajosEnCola(quantity);
                                            break;
                                        default:
                                            break;
                                    }
                                    Thread.sleep(20);
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }

                            }
                            stopAnimation.setVisible(false);
                        }
                    });
                    animation.start();
                    btnSimular.setDisable(false);
                }

            }
        });
        ejecucionNormal.start();
    }

    public void stopAnimation(ActionEvent actionEvent){
        animationContinue = false;
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

