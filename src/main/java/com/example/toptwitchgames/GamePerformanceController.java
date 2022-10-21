package com.example.toptwitchgames;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class GamePerformanceController implements Initializable {

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private TextField gameTextArea;

    @FXML
    private ChoiceBox<String> gameChoiceBox;
    @FXML
    private LineChart<String, Integer> lineChart;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int yearSelected;
    private int monthSelected;
    private String gameName;
    private int yearValue;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LineChart.Series<String, Integer> gamePerformance = new XYChart.Series<>();
        lineChart.setLegendVisible(false);
        lineChart.getXAxis().setLabel("Months");
        lineChart.getYAxis().setLabel("Average Viewers");
        lineChart.getData().addAll(DBUtility.getGamePerformance("League of Legends",2020));
        lineChart.getData().addAll(gamePerformance);

        yearComboBox.setValue(2020);
        yearComboBox.getItems().addAll(DBUtility.getDataYears());
        yearComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            lineChart.getData().clear();
            yearValue = newValue;
            gameChoiceBox.getItems().addAll(DBUtility.getDataGames(yearComboBox.getValue()));
            lineChart.getData().addAll(DBUtility.getGamePerformance(gameName, newValue));
        });
        gameChoiceBox.setValue("League of Legends");
        gameChoiceBox.getItems().addAll(DBUtility.getDataGames(yearComboBox.getValue()));
        gameChoiceBox.valueProperty().addListener((obs, oldValue, newValue)->{
            lineChart.getData().clear();
            gameName = newValue;
            lineChart.getData().addAll(DBUtility.getGamePerformance(newValue, yearComboBox.getValue()));
        });

    }
    public void exitTable(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("top-games-monthly-view.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}

