package com.example.toptwitchgames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBUtility {
    public static String user = "Connor1152990";
    public static String pw = "jz7vccKC96";
    private static String connURL = "jdbc:mysql://172.31.22.43:3306/Connor1152990";

    public static XYChart.Series<String, Integer> getTopGames(String monthSelected, Integer yearSelected) {
        XYChart.Series<String, Integer> topGames = new XYChart.Series<>();

        String sql = "SELECT Game, Avg_viewers " +
                "FROM TopTwitchGames " +
                "WHERE Year = " + yearSelected + " AND Month = " + monthSelected +
                " ORDER BY Avg_viewers DESC LIMIT 10;";
        try (
                Connection conn = DriverManager.getConnection(connURL,user,pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next()) {
                String name = resultSet.getString("Game");
                int avgViewers = resultSet.getInt("Avg_viewers");
                topGames.getData().add(new XYChart.Data<>(name, avgViewers));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return topGames;
    }

    public static ArrayList<Integer> getDataYears() {
        ArrayList<Integer> years = new ArrayList<>();

        String sql = "SELECT Year FROM TopTwitchGames\n" +
                "GROUP BY year;";
        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            //loop over the resultSet and create PieChart.Data objects
            while (resultSet.next()) {
                int year = resultSet.getInt("Year");
                years.add(year);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }
    public static ArrayList<String> getDataMonths() {
        ArrayList<String> months = new ArrayList<>();

        String sql = "SELECT Month FROM TopTwitchGames\n" +
                "GROUP BY Month;";
        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            //loop over the resultSet and create PieChart.Data objects
            while (resultSet.next()) {
                String month = resultSet.getString("Month");
                months.add(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return months;

    }

}
