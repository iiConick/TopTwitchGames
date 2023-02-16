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
    public static String user = "";
    public static String pw = "";
    private static String connURL = "";

    /**
     * This method is to populate the table view showing a more expanded view of the data displayed on the bar chart.\
     * I use the Game as my data type and pass the information from the sql query into the model. I then populate the array list
     * with the Game objects
     * @param monthSelected
     * @param yearSelected
     * @return
     */
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

    /**
     * This method populates the bar chart. It selects the x and y axis information from the database and stores them
     * in a XYChart.Series variable. Once all of the bars (10) in the chart are populated, it returns the chart to the controller.
     * The arguments passed in are from ComboBoxes in the UI.
     * @param monthSelected
     * @param yearSelected
     * @return
     */
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

    /**
     * This method gets the line chart information. Similar to the bar chart, it pulls the x and y values from the database
     * and populates a LineChart.Series variable. The arguments passed in are from the ComboBoxes in the UI
     * @param gameName
     * @param yearSelected
     * @return
     */
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

    /**
     * This method returns all of the years available in the database. This is to populate a ComboBox with years
     * relevant to the data.
     * @return
     */
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

    /**
     * This is to populate relevant months into a ComboBox.
     * @return
     */
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

    /**
     * This method retrieves an arraylist of game titles collected from the database based on popularity
     * this method checks if there is a matching game title, and if there isn't it adds the game to the arraylist.
     * If the current game already exists, it moves on to the next game.
     * @param yearSelected
     * @return
     */
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
