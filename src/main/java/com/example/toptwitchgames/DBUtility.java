package com.example.toptwitchgames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBUtility {
    public static String user = "Connor1152990";
    public static String pw = "jz7vccKC96";
    private static String connURL = "jdbc:mysql://172.31.22.43:3306/Connor1152990";


    public static ArrayList<Game> getTopGamesTable(int monthSelected, Integer yearSelected)
    {
        ArrayList<Game> topGamesTable = new ArrayList<>();
        String sql = "SELECT Game, Avg_viewers, Hours_watched, Hours_Streamed, Avg_channels\n" +
                "FROM TopTwitchGames\n" +
                "WHERE Year = " + yearSelected + " AND Month = " + monthSelected + "\n" +
                "ORDER BY Avg_viewers DESC LIMIT 50;";
        try (
                Connection conn = DriverManager.getConnection(connURL,user,pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                String game = resultSet.getString("Game");
                int avgViewers = resultSet.getInt("Avg_viewers");
                int hoursWatched = resultSet.getInt("Hours_watched");
                int hoursStreamed = resultSet.getInt("Hours_Streamed");
                int avgChannels = resultSet.getInt("Avg_channels");

                Game newGame = new Game(game, avgViewers, hoursWatched, hoursStreamed, avgChannels);
                topGamesTable.add(newGame);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return topGamesTable;

    }
    public static XYChart.Series<String, Integer> getTopGames(int monthSelected, Integer yearSelected) {
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

    public static LineChart.Series<String, Integer> getGamePerformance(String gameName, Integer yearSelected) {
        LineChart.Series<String, Integer> gamePerformance = new LineChart.Series<>();

        String sql = "SELECT Game, Avg_viewers, Year, Month\n" +
                "FROM TopTwitchGames\n" +
                "WHERE Game = '" + gameName + "' AND Year = " + yearSelected +"\n" +
                "ORDER BY Year, Month;";
        try (
                Connection conn = DriverManager.getConnection(connURL,user,pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                String month = resultSet.getString("Month");
                int avgViewers = resultSet.getInt("Avg_viewers");
                gamePerformance.getData().add(new XYChart.Data<>(month,avgViewers));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return gamePerformance;
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
    public static ArrayList<Integer> getDataMonths() {
        ArrayList<Integer> months = new ArrayList<>();

        String sql = "SELECT Month FROM TopTwitchGames\n" +
                "GROUP BY Month;";
        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            //loop over the resultSet and create PieChart.Data objects
            while (resultSet.next()) {
                int month = resultSet.getInt("Month");
                months.add(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return months;

    }
    public static ArrayList<String> getDataGames(int yearSelected)
    {
        boolean repeatedName = false;
        ArrayList<String> games = new ArrayList<>();

        String sql = "SELECT Game, Hours_watched FROM TopTwitchGames \n" +
                "WHERE Year = " + yearSelected + "\n" +
                "group by game, Hours_watched\n" +
                "ORDER BY Hours_watched DESC LIMIT 100;";

        try (
                Connection conn = DriverManager.getConnection(connURL, user, pw);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                String game = resultSet.getString("Game");
                for(String g : games)
                {
                    if(g.equals(game))
                    {
                        repeatedName = true;
                        break;
                    }
                }
                if(!repeatedName)
                    games.add(game);
                else
                    repeatedName = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

}
