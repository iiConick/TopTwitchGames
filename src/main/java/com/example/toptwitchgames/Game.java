package com.example.toptwitchgames;

public class Game {
    private String game;
    private int avgViewers;
    private int hoursWatched;
    private int hoursStreamed;
    private int avgChannels;

    public Game(String game, int avgViewers, int hoursWatched, int hoursStreamed, int avgChannels) {
       setGame(game);
       setAvgViewers(avgViewers);
       setHoursWatched(hoursWatched);
       setHoursStreamed(hoursStreamed);
       setAvgChannels(avgViewers);
    }
    public Game(String game, int avgViewers)
    {
        setGame(game);
        setAvgViewers(avgViewers);
    }

    public String getGame() {
        return game;
    }

    /**
     * Game could be anything on twitch, you can create your own category, so this is why I chose to not
     * validate more than checking if it is empty
     * @param game
     */
    public void setGame(String game) {
        if(game.isBlank() || game.isEmpty())
        {
            throw new IllegalArgumentException("Game must not be blank");
        }
        else
        {
            this.game = game;
        }

    }

    public int getAvgViewers() {
        return avgViewers;
    }

    /**
     * Validation protects the data from being unreadable
     * @param avgViewers
     */
    public void setAvgViewers(int avgViewers) {
        if(avgViewers < 0 || avgViewers > 1000000)
        {
            throw new IllegalArgumentException("Value of average viewers must be greater than 0 and less than 1000000");
        }
        else
        {
            this.avgViewers = avgViewers;
        }

    }

    public int getHoursWatched() {
        return hoursWatched;
    }

    /**
     * Hours watched is greater than 0
     * @param hoursWatched
     */
    public void setHoursWatched(int hoursWatched) {
        if(hoursWatched < 0)
        {
            throw new IllegalArgumentException("Value of hours watched must be greater than 0");
        }
        else
        {
            this.hoursWatched = hoursWatched;
        }
    }

    public int getHoursStreamed() {
        return hoursStreamed;
    }

    /**
     * Hours streamed can be a very high number so I chose not to cap it, it cant be below 0
     * @param hoursStreamed
     */
    public void setHoursStreamed(int hoursStreamed) {
        if(hoursStreamed < 0)
        {
            throw new IllegalArgumentException("Value of hours streamed must be greater than 0");
        }
        else
        {
            this.hoursStreamed = hoursStreamed;
        }
    }

    public int getAvgChannels() {
        return avgChannels;
    }

    /**
     * Average is capped to prevent unreadable data
     * @param avgChannels
     */
    public void setAvgChannels(int avgChannels) {
        if(avgChannels < 0 || avgChannels > 200000)
        {
            throw new IllegalArgumentException("Value of average channels must be greater than 0 and less than 200000");
        }
        else
        {
            this.avgChannels = avgChannels;
        }
    }
}
