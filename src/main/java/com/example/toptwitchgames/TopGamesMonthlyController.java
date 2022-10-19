package com.example.toptwitchgames;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TopGamesMonthlyController implements Initializable {
    public int yearValue = 0;
    public String monthValue;
    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> monthComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> topGames = new XYChart.Series<>();

        barChart.getData().addAll(DBUtility.getTopGames("01",2021));
        barChart.getData().addAll(topGames);
        yearComboBox.setValue(2021);
        monthComboBox.setValue("01");
        yearComboBox.getItems().addAll(DBUtility.getDataYears());
        yearComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            barChart.getData().clear();
            yearValue = newValue;
            barChart.getData().addAll(DBUtility.getTopGames(monthValue, newValue));
        });
        monthComboBox.getItems().addAll(DBUtility.getDataMonths());
        monthComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            barChart.getData().clear();
            monthValue = newValue;
            barChart.getData().addAll(DBUtility.getTopGames(newValue, yearValue));
        });

    }
}

