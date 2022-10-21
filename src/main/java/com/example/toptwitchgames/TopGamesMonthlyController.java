package com.example.toptwitchgames;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class TopGamesMonthlyController implements Initializable {
    public int yearValue = 0;
    public int monthValue = 0;
    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<Integer> monthComboBox;


    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Setting barchart parameters, populating the ComboBoxes, and calling the setData method
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        barChart.setLegendVisible(false);
        barChart.getXAxis().setLabel("Games");
        barChart.getYAxis().setLabel("Average Viewers");
        barChart.getData().clear();
        yearComboBox.getItems().addAll(DBUtility.getDataYears());
        monthComboBox.getItems().addAll(DBUtility.getDataMonths());
        setData(2020, 01);

    }

    /**
     * Switches the scene to the line graph view
     * @param event
     * @throws IOException
     */
    public void switchToLineGraph(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game-performance-view.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Game Performance");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Changes the scene to the table view of the bar chart with more data added in. It also calls the controller and method
     * setData from the Table Controller so infotmation about the month and year can be stored in between scenes
     * @param event
     * @throws IOException
     */
    public void switchToAvgViewersTable(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("top-games-monthly-table-view.fxml"));
        root = loader.load();

        scene = new Scene(root);

        TopGamesMonthlyTableController controller = loader.getController();
        controller.setData(yearValue, monthValue);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Month month = Month.of(monthValue);
        stage.setTitle(month + " " + yearValue + " STATS");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Initializes the bar chart to be shown, using arguments from the Table View. The arguments were originally
     * data from this class, but have been passed to the Table View and back. It sets up listeners to listen for changes in the
     * ComboBoxes. Once a change is sensed, the barchart will update all of the information accordingly.
     * @param year
     * @param month
     */
    public void setData(int year, int month)
    {
        barChart.getData().clear();
        yearValue = year;
        monthValue = month;

        Month stringMonth = Month.of(monthValue);
        titleLabel.setText("Top 50 Games of " + stringMonth +" "  +yearValue);
        if(monthValue == 0 && yearValue == 0)
        {
            monthValue = 01;
            yearValue = 2020;
        }
        yearComboBox.setValue(yearValue);
        monthComboBox.setValue(monthValue);
        Month newMonth = Month.of(monthValue);
        titleLabel.setText("Top 10 Games of " + newMonth + " "  + yearValue);
        XYChart.Series<String, Integer> topGames = new XYChart.Series<>();

        barChart.getData().addAll(DBUtility.getTopGames(monthValue,yearValue));
        barChart.getData().addAll(topGames);

        yearComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            barChart.getData().clear();
            yearValue = newValue;
            Month newMonth1 = Month.of(monthValue);
            titleLabel.setText("Top 10 Games of " + newMonth1 + " " + yearValue);
            barChart.getData().addAll(DBUtility.getTopGames(monthValue, newValue));

        });

        monthComboBox.valueProperty().addListener((obs, oldValue, newValue)->{
            barChart.getData().clear();
            monthValue = newValue;
            Month newMonth2 = Month.of(monthValue);
            titleLabel.setText("Top 10 Games of " + newMonth2 + " " + yearValue);
            barChart.getData().addAll(DBUtility.getTopGames(newValue, yearValue));


        });
    }
}

