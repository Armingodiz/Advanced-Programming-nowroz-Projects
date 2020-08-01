package com.company;
/**
 * this class extends from player class
 * and do the same thing but
 * automatically.
 *
 * @author ARMIN GODARZI
 */

import com.company.PlayGround;
import com.company.Player;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Bot extends Player {
    Random random = new Random();

    /**
     * @param numberOfPlayer number of player
     * @param playGround     the map which player plays in
     */
    public Bot(int numberOfPlayer, PlayGround playGround) {
        super(numberOfPlayer, playGround);
    }

    /**
     * this method first put nuts which their number is even to player number in updatePlayer method .
     * then we find the best location to put aur nut using getBestMoves and getBest methods.
     * the we check our location to be empty and valid to put nut in it.
     * if it was not valid we chose one of valid moves randomly.
     */
    public void makeMove() {
        updatePlayerStatus();
        playGround.printMap();
        String location;
        if (getBestMoves().size() == 0) {
            System.out.println("PASSED !   no best moves ");
            return;
        }
        System.out.println("PLAYER " + numberOfPlayer + " CHOOSING A SPOT ... ");
        location = getBest(getBestMoves());
        //checking location to be valid
        if (location.toCharArray().length == 2 && playGround.checkEmpty(location) && playGround.checkLegality(location, numberOfPlayer) && playGround.checkPassEachSpot(location, numberOfPlayer) != 0) {
            addNut(new Nut(numberOfPlayer, location));
            playGround.changeNuts(location, numberOfPlayer);//change color of some nuts base on our move.
        } else {
            int flag = 1;
            for (int k = 0; k < getBestMoves().size(); k++) {//for is here to if we could not find a good location we would not trap in loop
                // System.out.println(playGround.changeNuts(location, numberOfPlayer));
                System.out.println("PLEAS ENTER ANOTHER LOCATION :");
                int counter = 0;
                //chose some random location
                for (String temp2 : getBestMoves().keySet()) {
                    if (counter == k) {
                        location = temp2;
                        counter++;
                    } else
                        counter++;
                    System.out.println(" MOVES       " + temp2);
                }//check if location is valid or not
                if (location.toCharArray().length == 2 && playGround.checkEmpty(location)) {
                    if (playGround.checkLegality(location, numberOfPlayer) && playGround.checkPassEachSpot(location, numberOfPlayer) != 0) {
                        addNut(new Nut(numberOfPlayer, location));
                        playGround.changeNuts(location, numberOfPlayer);
                        flag = 0;
                        break;
                    }
                }
            }
            if (flag == 1)
                System.out.println("SORRY COULD NOT FIND A SPOT FOR NUT !!!");
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
                if (i <= 7 && j <= 7) {
                    //each if checks if this location is empty and valid or not
                    if (j - 1 >= 0 && map[i][j - 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i, j - 1), numberOfPlayer) != 0)
                        return true;
                    else if (j + 1 <= 7 && map[i][j + 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i, j + 1), numberOfPlayer) != 0)
                        return true;
                    else if (i - 1 >= 0 && map[i - 1][j].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j), numberOfPlayer) != 0)
                        return true;
                    else if (i + 1 <= 7 && map[i + 1][j].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j), numberOfPlayer) != 0)
                        return true;
                    else if (i + 1 <= 7 && j + 1 <= 7 && map[i + 1][j + 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j + 1), numberOfPlayer) != 0)
                        return true;
                    else if (i + 1 <= 7 && j - 1 >= 0 && map[i + 1][j - 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j - 1), numberOfPlayer) != 0)
                        return true;
                    else if (j + 1 <= 7 && i - 1 >= 0 && map[i - 1][j + 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j + 1), numberOfPlayer) != 0)
                        return true;
                    else if (i - 1 >= 0 && j - 1 >= 0 && map[i - 1][j - 1].getNumberOfPlayer() == 3 && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j - 1), numberOfPlayer) != 0)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * this method checks all of other player nuts around
     * it checks each 8 direction and if any direction was valid put it in map
     * and in the end get us the map
     *
     * @return a hash map of available moves
     */
    public HashMap<String, Integer> getBestMoves() {
        HashMap<String, Integer> moves;
        moves = new HashMap<String, Integer>();
        playGround.updateMap();
        String output = "";
        Nut[][] map = playGround.getMapOfNuts();
        for (Nut temp : playGround.getNuts()) {
            if (temp.getNumberOfPlayer() == 1) {
                int i = playGround.getI(temp.getLocation());
                int j = playGround.getJ(temp.getLocation());
                if (i <= 7 && j <= 7) {
                    //each if checks if spot is valid and empty or not if it was put location as key and number of nuts it can changes their color as value in hash map
                    if (j - 1 >= 0 && playGround.checkEmpty(playGround.getLocation(i, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i, j - 1), numberOfPlayer) != 0) {
                        moves.put(playGround.getLocation(i, j - 1), playGround.checkPassEachSpot(playGround.getLocation(i, j - 1), numberOfPlayer));
                    }  if (j + 1 <= 7 && playGround.checkEmpty(playGround.getLocation(i, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i, j + 1), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i, j + 1), playGround.checkPassEachSpot(playGround.getLocation(i, j + 1), numberOfPlayer));
                    if (i - 1 >= 0 && playGround.checkEmpty(playGround.getLocation(i - 1, j)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i - 1, j), playGround.checkPassEachSpot(playGround.getLocation(i - 1, j), numberOfPlayer));
                    if (i + 1 <= 7 && playGround.checkEmpty(playGround.getLocation(i + 1, j)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i + 1, j), playGround.checkPassEachSpot(playGround.getLocation(i + 1, j), numberOfPlayer));
                    if (i + 1 <= 7 && j + 1 <= 7 && playGround.checkEmpty(playGround.getLocation(i + 1, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j + 1), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i + 1, j + 1), playGround.checkPassEachSpot(playGround.getLocation(i + 1, j + 1), numberOfPlayer));
                    if (i + 1 <= 7 && j - 1 >= 0 && playGround.checkEmpty(playGround.getLocation(i + 1, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i + 1, j - 1), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i + 1, j - 1), playGround.checkPassEachSpot(playGround.getLocation(i + 1, j - 1), numberOfPlayer));
                    if (j + 1 <= 7 && i - 1 >= 0 && playGround.checkEmpty(playGround.getLocation(i - 1, j + 1)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j + 1), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i - 1, j + 1), playGround.checkPassEachSpot(playGround.getLocation(i - 1, j + 1), numberOfPlayer));
                    if (i - 1 >= 0 && j - 1 >= 0 && playGround.checkEmpty(playGround.getLocation(i - 1, j - 1)) && playGround.checkPassEachSpot(playGround.getLocation(i - 1, j - 1), numberOfPlayer) != 0)
                        moves.put(playGround.getLocation(i - 1, j - 1), playGround.checkPassEachSpot(playGround.getLocation(i - 1, j - 1), numberOfPlayer));
                }
            }
        }
        return moves;
    }

    /**
     * this method find the best move base on the number of nuts it can changes their color
     *
     * @param validMoves a hash map of available moves
     * @return best spot
     */
    public String getBest(HashMap<String, Integer> validMoves) {
        int max = 0;
        String location = "";
        for (String temp : validMoves.keySet()) {
            if (validMoves.get(temp) > max) {
                max = validMoves.get(temp);
                location = temp;
            }
        }
        return location;
    }
}