package com.example.toptwitchgames;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class TopGamesMonthlyTableController implements Initializable {
    @FXML
    private TableView<Game> tableView;

    @FXML
    private TableColumn<Game, Integer> totalHoursWatchedColumn;

    @FXML
    private TableColumn<Game, Integer> averageChannelsLiveColumn;

    @FXML
    private TableColumn<Game, String> gameTitleColumn;

    @FXML
    private TableColumn<Game, Integer> averageViewersColumn;

    @FXML
    private TableColumn<Game, Integer> totalHoursStreamedColumn;
    @FXML
    private Label titleLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int yearSelected;
    private int monthSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void setData(int year, int month)
    {
        yearSelected = year;
        monthSelected = month;
        Month stringMonth = Month.of(monthSelected);
        titleLabel.setText("Top 50 Games of " + stringMonth +" "  +yearSelected);
        gameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("game"));
        averageViewersColumn.setCellValueFactory(new PropertyValueFactory<>("avgViewers"));
        totalHoursWatchedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWatched"));
        totalHoursStreamedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursStreamed"));
        averageChannelsLiveColumn.setCellValueFactory(new PropertyValueFactory<>("avgChannels"));
        tableView.getItems().addAll(DBUtility.getTopGamesTable(monthSelected, yearSelected));
    }
    public void switchToBarGraph(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("top-games-monthly-view.fxml"));
        root = loader.load();
        TopGamesMonthlyController controller = loader.getController();
        controller.setData(yearSelected, monthSelected);
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Month month = Month.of(monthSelected);
        stage.setTitle(month + " " + yearSelected + " STATS");
        stage.setScene(scene);
        stage.show();
    }
}

