/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optima;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 *
 * @author Lord-Karim
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private JFXRadioButton dejong;
    @FXML private JFXTextField dejong_min;
    @FXML private JFXTextField dejong_max;
    @FXML private JFXRadioButton aphe;
    @FXML private JFXTextField aphe_min;
    @FXML private JFXTextField aphe_max;
    @FXML private JFXRadioButton schwefel;
    @FXML private JFXTextField schwefel_min;
    @FXML private JFXTextField schwefel_max;
    @FXML private JFXRadioButton rastrigin;
    @FXML private JFXTextField rastrigin_min;
    @FXML private JFXTextField rastrigin_max;
    @FXML private JFXRadioButton maphe;
    @FXML private JFXTextField maphe_min;
    @FXML private JFXTextField maphe_max;
    @FXML private JFXRadioButton sodp;
    @FXML private JFXTextField sodp_min;
    @FXML private JFXTextField sodp_max;
    @FXML private JFXRadioButton michal;
    @FXML private JFXTextField michal_min;
    @FXML private JFXTextField michal_max;
    @FXML private JFXRadioButton ackley;
    @FXML private JFXTextField ackley_min;
    @FXML private JFXTextField ackley_max;
    @FXML private JFXRadioButton griewangk;
    @FXML private JFXTextField griewangk_min;
    @FXML private JFXTextField griewangk_max;
    @FXML private JFXRadioButton rbv;
    @FXML private JFXTextField rbv_min;
    @FXML private JFXTextField rbv_max;
    @FXML private JFXRadioButton rhe;
    @FXML private JFXTextField rhe_min;
    @FXML private JFXTextField rhe_max;
    @FXML private JFXTextField input;
    @FXML private JFXTextField width;
    @FXML private JFXTextField height;
    @FXML private JFXTextField nbrItiration;
    @FXML private JFXButton calcul;
    @FXML private JFXButton clear;
    @FXML private JFXButton generate;
    @FXML private Label output;
    @FXML private JFXTreeTableView<Cols> table;
    @FXML private LineChart<?, ?> graph;
    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;
    @FXML private JFXCheckBox def;
    @FXML private JFXCheckBox bat;
    @FXML private JFXCheckBox cutlfish;
    @FXML private JFXCheckBox pso;
    @FXML private JFXCheckBox firefly;
    @FXML private JFXCheckBox local;
    @FXML private ComboBox arrondiCombo;
    @FXML private JFXButton arrondiButton;
    @FXML private TextArea poparea;

    
    ObservableList<String> arrondiList=FXCollections.observableArrayList("100.0","1000.0","10000.0","100000.0","1000000.0","10000000.0","100000000.0","1000000000.0","10000000000.0");
    ObservableList<Cols> columns = FXCollections.observableArrayList();
    
    private String functionNam;
    private ArrayList bestSolutionDisplay=new ArrayList();
    private ArrayList bestSolutionDisplayArrondi=new ArrayList();
    private ArrayList minList=new ArrayList();
    private ArrayList bestSolution=new ArrayList();
    private double lowerBound,upperBound;
    private ToggleGroup functionsRadioGroupe=new ToggleGroup();
    private double[] initArraySolution;
    ArrayList initList=new ArrayList();
    private double [][] pop;
    Random rand=new Random();
    private String algoNam;
    private Color color ;
    private XYChart.Series dots =new XYChart.Series();
    
     
    
    @FXML
    void generateAction(ActionEvent event) {
        poparea.clear();
        if (dejong.isSelected()) {
                functionNam="dejong"; lowerBound=-5.12;upperBound=5.12;
            }else if(aphe.isSelected()){
                functionNam="aphe";lowerBound=-5.12;upperBound=5.12;
            }else if(schwefel.isSelected() ){
                functionNam="schwefel";lowerBound=-500;upperBound=500;
            }else if(rastrigin.isSelected()){
                functionNam="rastrigin";lowerBound=-5.12;upperBound=5.12;
            }else if(maphe.isSelected()){
                functionNam="maphe";lowerBound=-5.12;upperBound=5.12;
            }else if(sodp.isSelected()){
                functionNam="sodp";lowerBound=-1;upperBound=1;
            }else if(michal.isSelected()){
                functionNam="michal";lowerBound=0;upperBound=Math.PI;
            }else if(ackley.isSelected()){
                functionNam="ackley";lowerBound=-1;upperBound=1;
            }else if(griewangk.isSelected()){
                functionNam="griewangk";lowerBound=-600;upperBound=600;
            }else if(rbv.isSelected()){
                functionNam="rbv";lowerBound=-2.048;upperBound=2.048;
            }else if(rhe.isSelected()){
                functionNam="rhe";lowerBound=-65.536;upperBound=65.536;
            }
        
        int width_size=Integer.parseInt(width.getText());
        int height_size=Integer.parseInt(height.getText());
        
        
        
        pop =new double[height_size][width_size];
        
        for(int i=0; i<height_size ;i++)
            for(int j=0; j<width_size;j++)
                pop[i][j]=lowerBound +(upperBound - (lowerBound))*rand.nextDouble();
        
        for(int i=0; i<height_size ;i++){
            for(int j=0; j<width_size;j++)
                poparea.appendText(pop[i][j]+"\t");
            poparea.appendText("\n");}
    }
    
    
    @FXML
    void calculAction(ActionEvent event) {
        bestSolution.clear();
        bestSolutionDisplay.clear();
        //initialisation de dimention
        int width_size=Integer.parseInt(width.getText());
        int height_size=Integer.parseInt(height.getText());
        int nbrItir=Integer.parseInt(nbrItiration.getText());
        
        
        
        //**************************les fonctions  ***************************//
        if (dejong.isSelected()) {
                functionNam="dejong"; lowerBound=-5.12;upperBound=5.12;
            }else if(aphe.isSelected()){
                functionNam="aphe";lowerBound=-5.12;upperBound=5.12;
            }else if(schwefel.isSelected() ){
                functionNam="schwefel";lowerBound=-500;upperBound=500;
            }else if(rastrigin.isSelected()){
                functionNam="rastrigin";lowerBound=-5.12;upperBound=5.12;
            }else if(maphe.isSelected()){
                functionNam="maphe";lowerBound=-5.12;upperBound=5.12;
            }else if(sodp.isSelected()){
                functionNam="sodp";lowerBound=-1;upperBound=1;
            }else if(michal.isSelected()){
                functionNam="michal";lowerBound=0;upperBound=Math.PI;
            }else if(ackley.isSelected()){
                functionNam="ackley";lowerBound=-1;upperBound=1;
            }else if(griewangk.isSelected()){
                functionNam="griewangk";lowerBound=-600;upperBound=600;
            }else if(rbv.isSelected()){
                functionNam="rbv";lowerBound=-2.048;upperBound=2.048;
            }else if(rhe.isSelected()){
                functionNam="rhe";lowerBound=-65.536;upperBound=65.536;
            }
        
        //**************************local algo******************
        if(local.isSelected()){
            Local algoLocal=new Local(functionNam, nbrItir, 10, height_size, lowerBound, upperBound, pop);
            algoLocal.local();
            bestSolution=algoLocal.getBestSolution();
            minList=algoLocal.getMinList();
            algoNam="local";
            
            
           //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        double a =(double) minList.get(minList.size()-1);
        a= Math.round(a*1000.0)/1000.0;
        System.out.println(a);
        
        bestSolutionDisplay.clear();
        minList.clear();
        }//**************************algorithm Global ***************************//
        if(def.isSelected()){
            Global algo=new Global(functionNam, nbrItir, 10, height_size, lowerBound, upperBound, pop);
            algo.local();
            bestSolution=algo.getBestSolution();
            minList=algo.getMinList();
            algoNam="global";
            
           //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        double a =(double) minList.get(minList.size()-1);
        a= Math.round(a*1000.0)/1000.0;
        System.out.println(a);
        
        bestSolutionDisplay.clear();
        minList.clear();
        } //**************************algorithm BAT ***************************//
         if(bat.isSelected()){
            Bat batalgo=new Bat(functionNam, nbrItir, 10, height_size, lowerBound, upperBound, pop);  
            batalgo.startBat();
            bestSolution= batalgo.getBestSolution();
            minList=batalgo.getminList();
            algoNam="BAT";
            
            //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        double a =(double) minList.get(minList.size()-1);
        a= Math.round(a*1000.0)/1000.0;
        System.out.println(a);
        
        bestSolutionDisplay.clear();
        minList.clear();
        } //**************************algorithm PSO ***************************//
         if(pso.isSelected()){
            PSO psoAlgo = new PSO(functionNam, nbrItir, 10, height_size, lowerBound,upperBound, pop);
            psoAlgo.psoAlgo();
            bestSolution=psoAlgo.getBestSolution();
            minList=psoAlgo.getgBesValueList();
            algoNam="PSO";
            
            
            //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        double a =(double) minList.get(minList.size()-1);
        a= Math.round(a*1000.0)/1000.0;
        System.out.println(a);
        
        bestSolutionDisplay.clear();
        minList.clear();
        }//***************************algorithm cutlfish***********************//
         if(cutlfish.isSelected()){
            Cuttelfish cuttelfishAlgo = new Cuttelfish(functionNam, nbrItir, 10, height_size,lowerBound,upperBound, pop);
            cuttelfishAlgo.algoFish();
            bestSolution=cuttelfishAlgo.getBestSolution();
            minList=cuttelfishAlgo.getMinList();
            algoNam="cutlefish";
            
            
            
            //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        double a =(double) minList.get(minList.size()-1);
        a= Math.round(a*1000.0)/1000.0;
        System.out.println(a);
        
        bestSolutionDisplay.clear();
        minList.clear();
        }//***************************algorithm firefly***********************//
         if(firefly.isSelected()){
            Firefly fir = new Firefly(functionNam, nbrItir,Integer.parseInt(height.getText()), 10, height_size,lowerBound,upperBound, pop);
            fir.FireFlyAlgo();
            bestSolution=fir.getBestSolution();
            minList=fir.getMinList();
            algoNam="firefly";
            
            
            //around solution
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplay.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
            //Best Solution Table
        columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);
        
        //graph.getData().remove(dots);
        
        bestSolutionDisplay.clear();
        minList.clear();
        }
         
         
         
        
        //Best Solution Table
        /*columns.add(new Cols(
                 algoNam.toString()
                ,bestSolutionDisplay.get(0).toString()
                ,bestSolutionDisplay.get(1).toString()
                ,bestSolutionDisplay.get(2).toString()
                ,bestSolutionDisplay.get(3).toString()
                ,bestSolutionDisplay.get(4).toString()
                ,bestSolutionDisplay.get(5).toString()
                ,bestSolutionDisplay.get(6).toString()
                ,bestSolutionDisplay.get(7).toString()
                ,bestSolutionDisplay.get(8).toString()
                ,bestSolutionDisplay.get(9).toString()));
        
        //List mins Charte
        XYChart.Series dots =new XYChart.Series();
            for(Integer i=0;i<nbrItir;i++){
                dots.getData().add(new XYChart.Data(i.toString(),minList.get(i)));
            }
        dots.setName(algoNam);
        graph.getData().addAll(dots);*/
        
    }
    
    @FXML
    void clearAction(ActionEvent event) {
        graph.getData().clear();
        columns.clear();
    }
    
    
     //arrindi Bottun
    @FXML
    void arrondiAction(ActionEvent event) {
        columns.clear();
        bestSolutionDisplayArrondi.clear();
        for(int i=0; i<bestSolution.size();i++){
                bestSolutionDisplayArrondi.add(Math.round((double)bestSolution.get(i)*Double.parseDouble((String) arrondiCombo.getValue()))/Double.parseDouble((String) arrondiCombo.getValue()));
        }
        
        //Best Solution Table
        columns.add(new Cols(
                algoNam.toString()
                ,bestSolutionDisplayArrondi.get(0).toString()
                ,bestSolutionDisplayArrondi.get(1).toString()
                ,bestSolutionDisplayArrondi.get(2).toString()
                ,bestSolutionDisplayArrondi.get(3).toString()
                ,bestSolutionDisplayArrondi.get(4).toString()
                ,bestSolutionDisplayArrondi.get(5).toString()
                ,bestSolutionDisplayArrondi.get(6).toString()
                ,bestSolutionDisplayArrondi.get(7).toString()
                ,bestSolutionDisplayArrondi.get(8).toString()
                ,bestSolutionDisplayArrondi.get(9).toString()));
    }
       
    
    //--------------------------------------------------------------------------------tableview

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //functions radio groupe
        dejong.setToggleGroup(functionsRadioGroupe);
        aphe.setToggleGroup(functionsRadioGroupe);
        schwefel.setToggleGroup(functionsRadioGroupe);
        rastrigin.setToggleGroup(functionsRadioGroupe);
        maphe.setToggleGroup(functionsRadioGroupe);
        sodp.setToggleGroup(functionsRadioGroupe);
        michal.setToggleGroup(functionsRadioGroupe);
        ackley.setToggleGroup(functionsRadioGroupe);
        griewangk.setToggleGroup(functionsRadioGroupe);
        rbv.setToggleGroup(functionsRadioGroupe);
        rhe.setToggleGroup(functionsRadioGroupe);
        
        //tabele solution
        JFXTreeTableColumn<Cols,String> algo=new JFXTreeTableColumn<>("algo");
        algo.setPrefWidth(110);
        algo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().algo;
            }
        });
        JFXTreeTableColumn<Cols,String> col1=new JFXTreeTableColumn<>("X1");
        col1.setPrefWidth(110);
        col1.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col1;
            }
        });
        JFXTreeTableColumn<Cols,String> col2=new JFXTreeTableColumn<>("X2");
        col2.setPrefWidth(110);
        col2.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col2;
            }
        });
        JFXTreeTableColumn<Cols,String> col3=new JFXTreeTableColumn<>("X3");
        col3.setPrefWidth(110);
        col3.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col3;
            }
        });
        JFXTreeTableColumn<Cols,String> col4=new JFXTreeTableColumn<>("X4");
        col4.setPrefWidth(110);
        col4.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col4;
            }
        });
        JFXTreeTableColumn<Cols,String> col5=new JFXTreeTableColumn<>("X5");
        col5.setPrefWidth(110);
        col5.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col5;
            }
        });
        JFXTreeTableColumn<Cols,String> col6=new JFXTreeTableColumn<>("X6");
        col6.setPrefWidth(110);
        col6.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col6;
            }
        });
        JFXTreeTableColumn<Cols,String> col7=new JFXTreeTableColumn<>("X7");
        col7.setPrefWidth(110);
        col7.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col7;
            }
        });
        JFXTreeTableColumn<Cols,String> col8=new JFXTreeTableColumn<>("X8");
        col8.setPrefWidth(110);
        col8.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col8;
            }
        });
        JFXTreeTableColumn<Cols,String> col9=new JFXTreeTableColumn<>("X9");
        col9.setPrefWidth(110);
        col9.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col9;
            }
        });
        JFXTreeTableColumn<Cols,String> col10=new JFXTreeTableColumn<>("X10");
        col10.setPrefWidth(110);
        col10.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cols, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cols, String> param) {
                return param.getValue().getValue().col10;
            }
        });
        
        final TreeItem<Cols> root = new RecursiveTreeItem<Cols>(columns, RecursiveTreeObject::getChildren);
        table.getColumns().setAll(algo,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10);
        table.setRoot(root);
        table.setShowRoot(false);
        // TODO
        arrondiCombo.setValue("100.0");
        arrondiCombo.setItems(arrondiList);
        
    }    
    
    
    class Cols extends RecursiveTreeObject<Cols>{
        StringProperty algo;
        StringProperty col1;
        StringProperty col2;
        StringProperty col3;
        StringProperty col4;
        StringProperty col5;
        StringProperty col6;
        StringProperty col7;
        StringProperty col8;
        StringProperty col9;
        StringProperty col10;
        
        
        public Cols(String algo, String col1,String col2,String col3,String col4,String col5,String col6,String col7,String col8,String col9,String col10){
            this.algo=new SimpleStringProperty(algo);
            this.col1=new SimpleStringProperty(col1);
            this.col2=new SimpleStringProperty(col2);
            this.col3=new SimpleStringProperty(col3);
            this.col4=new SimpleStringProperty(col4);
            this.col5=new SimpleStringProperty(col5);
            this.col6=new SimpleStringProperty(col6);
            this.col7=new SimpleStringProperty(col7);
            this.col8=new SimpleStringProperty(col8);
            this.col9=new SimpleStringProperty(col9);
            this.col10=new SimpleStringProperty(col10);
        }
    }
    
}
