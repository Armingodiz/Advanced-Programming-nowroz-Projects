package com.company;

/**
 * this class holds information of each nut like number of
 * player and location of nut.
 *  @author ARMIN GODARZI
 */
public class Nut {
    private int numberOfPlayer;
    private String location;

    /**
     *
     * @param numberOfPlayer number of player who has the nut
     * @param location location of nut in map
     */
    public Nut(int numberOfPlayer, String location) {
        this.numberOfPlayer = numberOfPlayer;
        this.location = location;
    }

    /**
     *
     * @return number of player who has this nut
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     *
     * @param numberOfPlayer is number of player who has the nut
     */
    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    /**
     *
     * @return the location of nut
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location is the location of nut
     */
    public void setLocation(String location) {
        this.location = location;
    }

}

