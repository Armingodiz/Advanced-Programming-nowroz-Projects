package com.company;
/**
 * this class is for player and hold its information
 * and have methods to add or remove nut , make a move , check pass and etc.
 *
 * @author ARMIN GODARZI
 */

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Player {
    protected ArrayList<Nut> nuts;
    protected int numberOfPlayer;
    protected PlayGround playGround;

    /**
     * @param numberOfPlayer number of player
     * @param playGround     the play ground we want to play in
     */
    public Player(int numberOfPlayer, PlayGround playGround) {
        nuts = new ArrayList<Nut>();
        this.numberOfPlayer = numberOfPlayer;
        this.playGround = playGround;
    }

    /**
     * @param nut the nut we want to add it to game
     */
    public void addNut(Nut nut) {
        nuts.add(nut);
        playGround.getNuts().add(nut);
    }

    /**
     * @param nut the nut we want to remove it
     */
    public void removeNut(Nut nut) {
        nuts.remove(nut);
    }

    /**
     * this method first checks all the nuts in game and put each nut that its number
     * is even to number of player to player nuts
     * then checks player nuts and then remove the nuts that their number is not even
     * to player nuts from player nuts.
     */
    public void updatePlayerStatus() {
        for (Nut temp : playGround.getNuts()) {
            if (nuts.indexOf(temp) == -1 && temp.getNumberOfPlayer() == numberOfPlayer)
                nuts.add(temp);
        }
        for (int i = 0; i < nuts.size(); i++) {
            if (nuts.get(i).getNumberOfPlayer() != numberOfPlayer)
                nuts.remove(nuts.get(i));
        }
    }

    /**
     * this method first put nuts which their number is even to player number in updatePlayer method .
     * then we get location from user
     * the we check our location to be empty and valid to put nut in it.
     * if it was not valid we get another location from user.
     */
    public void makeMove() {
        updatePlayerStatus();
        //playGround.printMap();
        Scanner myscanner = new Scanner(System.in);
        String location;
        System.out.println("PLAYER " + numberOfPlayer + " CHOSE A SPOT FOR YOUR NUT : ");
        location = myscanner.next();
        //checking location to be valid
        if (playGround.checkEmpty(location) && playGround.checkLegality(location, numberOfPlayer) && playGround.checkPassEachSpot(location, numberOfPlayer) != 0) {
            addNut(new Nut(numberOfPlayer, location));
            playGround.changeNuts(location, numberOfPlayer);
        } else {
            int flag = 1;
            while (flag == 1) {
                System.out.println(playGround.changeNuts(location, numberOfPlayer));
                System.out.println("PLEAS ENTER ANOTHER LOCATION :");
                location = myscanner.next();
                if (playGround.checkEmpty(location)) {
                    if (playGround.checkLegality(location, numberOfPlayer) && playGround.checkPassEachSpot(location, numberOfPlayer) != 0) {
                        addNut(new Nut(numberOfPlayer, location));
                        playGround.changeNuts(location, numberOfPlayer);
                        flag = 0;
                    }
                }
            }
        }

    }

    /**
     * this method checks each 8 available direction around each nut of other player
     * to check if there is a valid spot or not
     *
     * @return true if we have available move  or false if we did not.
     */
    public boolean checkPass() {
        playGround.updateMap();
        Nut[][] map = playGround.getMapOfNuts();
        for (Nut temp : playGround.getNuts()) {
            if (temp.getNumberOfPlayer() != numberOfPlayer) {
                int i = playGround.getI(temp.getLocation());
                int j = playGround.getJ(temp.getLocation());
                if (i <= 7 && j <= 7 && i >= 0 && j >= 0) {
                    if (j - 1 >= 0 && (playGround.checkEmpty(playGround.getLocation(i, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i, j - 1), numberOfPlayer) != 0))
                        return true;
                    if (j + 1 <= 7 && (playGround.checkEmpty(playGround.getLocation(i, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i, j + 1), numberOfPlayer) != 0))
                        return true;
                    if (i - 1 >= 0 && (playGround.checkEmpty(playGround.getLocation(i - 1, j)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j), numberOfPlayer) != 0))
                        return true;
                    if (i + 1 <= 7 && (playGround.checkEmpty(playGround.getLocation(i + 1, j)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j), numberOfPlayer) != 0))
                        return true;
                    if (i + 1 <= 7 && j + 1 <= 7 && (playGround.checkEmpty(playGround.getLocation(i + 1, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j + 1), numberOfPlayer) != 0))
                        return true;
                    if (i + 1 <= 7 && j - 1 >= 0 && (playGround.checkEmpty(playGround.getLocation(i + 1, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j - 1), numberOfPlayer) != 0))
                        return true;
                    if (j + 1 <= 7 && i - 1 >= 0 && (playGround.checkEmpty(playGround.getLocation(i - 1, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j + 1), numberOfPlayer) != 0))
                        return true;
                    if (i - 1 >= 0 && j - 1 >= 0 && (playGround.checkEmpty(playGround.getLocation(i - 1, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j - 1), numberOfPlayer) != 0))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * @return nuts that player has
     */
    public ArrayList<Nut> getNuts() {
        return nuts;
    }
    public int getCountOfNuts(){
        return nuts.size();
    }
}