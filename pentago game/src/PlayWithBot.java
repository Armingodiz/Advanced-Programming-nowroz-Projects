package com.company;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * this class is to add marble and rotate parts automatically
 */
public class PlayWithBot {
    private PlayGround playground;
    private int[][] map;

    /**
     * @param playground is play ground
     */
    public PlayWithBot(PlayGround playground) {
        this.playground = playground;
        map = playground.getMap();
    }

    /**
     * this method check all empty spots and return the location that make longest trial possible
     * or stop longest trial of opponent using find trial method which is in play ground class
     *
     * @param numberOfPlayer is number of player
     * @return best spot as i and j
     */
    public int[] findBestSpot(int numberOfPlayer) {
        int[] spot = new int[2];
        for (int k = 0; k < 5; k++) {//start searching for longest trial of bot marbles or opponent marbles
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (map[i][j] == 0) {//search only between empty spots
                        //System.out.println("LOCATION : X =  " + j + "  Y = " + i + "  TRIAL+ = " + playground.findTrail(i, j, numberOfPlayer) + "  TRIAL- = " + playground.findTrail(i, j, 1 + numberOfPlayer % 2));
                        if (playground.findTrail(i, j, numberOfPlayer) >= 5 - k || playground.findTrail(i, j, 1 + numberOfPlayer % 2) >= 5 - k) {
                            spot[0] = j;
                            spot[1] = i;
                            //System.out.println(spot[0] + "  " + spot[1]);
                            return spot;
                        }
                    }
                }
            }
        }
        boolean flag = true;
        while (flag) {//if we could not find any empty spot
            Random random = new Random();
            int x = random.nextInt(5), y = random.nextInt(5);
            if (map[x][y] == 0) {
                spot[0] = x;
                spot[1] = y;
                System.out.println("RANDOM SPOT : " + spot[0] + "  " + spot[1]);
                return spot;
            }
        }
        return spot;
    }

    /**
     * this method return a array which includes 4 number
     * number of trials of bot and opponent and length of their longest trial
     *
     * @param numberOfPlayer number of player
     * @return a array
     */
    public int[] getStatus(int numberOfPlayer) {
        int[] status = new int[4];
        int maxTrial = 1, countOfTrial = 0;
        int maxOpponentTrial = 1, countOfOpponentTrial = 0;
        playground.updateMap();
        int[][] map = playground.getMap();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] == numberOfPlayer) {
                    int trial = playground.findTrail(i, j, numberOfPlayer);
                    if (trial > maxTrial)
                        maxTrial = trial;
                    if (trial >= 1)
                        countOfTrial += trial;
                } else if (map[i][j] == 1 + numberOfPlayer % 2) {
                    int trial = playground.findTrail(i, j, 1 + numberOfPlayer % 2);
                    if (trial > maxOpponentTrial)
                        maxOpponentTrial = trial;
                    if (trial >= 1)
                        countOfOpponentTrial += trial;
                }
            }
        }
        status[0] = countOfTrial;
        status[1] = maxTrial;
        status[2] = countOfOpponentTrial;
        status[3] = maxOpponentTrial;
        return status;
    }

    /**
     * this method rotate each part in 2 direction and compare status of map before and after rotation
     * it puts difference between status of bot and opponent in an array
     * then compare rotations to each other and chose the best one based on change of status
     *
     * @param numberOfPlayer is number of player
     * @param typeOfPlay     is a variable which determine priority is winning and making trial or defending and stop opponent from making trial
     * @return a array that includes number of part and direction
     */
    public int[] findBestRotation(int numberOfPlayer, int typeOfPlay) {
        int[] bestRotation1 = new int[2];
        bestRotation1[0] = 10;
        int[] bestRotation2 = new int[2];
        bestRotation2[0] = 10;
        int[][] totalStatus = new int[8][2];
        int counter = 0;
        for (int k = 0; k < 4; k++) {//changing each part
            int[] oldStatus = getStatus(numberOfPlayer);
            playground.rotatePart(k + 1, 1);
            int[] newStatus = getStatus(numberOfPlayer);
            totalStatus[counter][0] = newStatus[0] - oldStatus[0];
            totalStatus[counter][0] += newStatus[1] - oldStatus[1];//this variable hold count of good changes , the bigger this variable is the better this rotation is
            totalStatus[counter][1] = newStatus[2] - oldStatus[2];
            totalStatus[counter][1] += newStatus[3] - oldStatus[3];//this variable hold count of bad changes for opponent ,the smaller this variable is the better this rotation is
            counter++;
            playground.rotatePart(k + 1, 2);
            playground.rotatePart(k + 1, 2);
            int[] newStatus1 = getStatus(numberOfPlayer);
            totalStatus[counter][0] = newStatus1[0] - oldStatus[0];
            totalStatus[counter][0] += newStatus1[1] - oldStatus[1];
            totalStatus[counter][1] = newStatus1[2] - oldStatus[2];
            totalStatus[counter][1] += newStatus1[3] - oldStatus[3];
            counter++;
            playground.rotatePart(k + 1, 1);//rotating to stop map from changing
        }
       /* for (int x = 0; x < 8; x++) {
            if (x % 2 == 0)
                System.out.println(" PART NUMBER : " + (x / 2 + 1) + "  direction =  1 " + "  +TOTAL :  " + totalStatus[x][0] + " -TOTAL  : " + totalStatus[x][1]);
            else
                System.out.println(" PART NUMBER : " + ((x - 1) / 2 + 1) + "  direction =  2 " + "  +TOTAL :  " + totalStatus[x][0] + " -TOTAL  : " + totalStatus[x][1]);
        }*/
        HashMap<Integer, Integer> choices = new HashMap<Integer, Integer>();//list of maximum good changes for bot which make longer trial and cause shorter trials for opponent
        int max = 0, maxI = 100;
        for (int i = 0; i < 8; i++) {
            if (totalStatus[i][0] > max)
                max = totalStatus[i][0];//determining max good change
        }
        for (int i = 0; i < 8; i++) {
            if (totalStatus[i][0] == max)
                choices.put(i, totalStatus[i][1]);//holding maximum good changes with different count of bad changes for opponent
        }
        int totalGood = 100;
        for (int temp : choices.keySet()) {
            if (choices.get(temp) <= totalGood) {
                maxI = temp;
                totalGood = choices.get(temp);//finding the rotation which cause best change for bot and worst for opponent
            }
        }
        /*putting best rotation details base on index of rotation */
        if (maxI % 2 == 0) {
            bestRotation1[0] = (maxI / 2) + 1;
            bestRotation1[1] = 1;
        } else {
            bestRotation1[0] = (maxI + 1) / 2;
            bestRotation1[1] = 2;
        }
        HashMap<Integer, Integer> choices1 = new HashMap<Integer, Integer>();//list of trials causes  worst change for opponent whit different count of good changes for bot
        int min = 0, minI = 100;
        for (int i = 0; i < 8; i++) {
            if (totalStatus[i][1] <= min)
                min = totalStatus[i][1];//finding min
        }
        for (int i = 0; i < 8; i++) {
            if (totalStatus[i][1] == min)
                choices1.put(i, totalStatus[i][0]);//holding minimums count with different count of good changes for bot
        }
        int totalBad = -100;
        for (int temp : choices1.keySet()) {
            if (choices1.get(temp) >= totalBad) {
                minI = temp;
                totalBad = choices1.get(temp);//finding rotation which cause worst change for opponent and best for bot
            }
        }
        /*putting best rotation details base on index of rotation */
        if (minI % 2 == 0) {
            bestRotation2[0] = (minI / 2) + 1;
            bestRotation2[1] = 1;
        } else {
            bestRotation2[0] = (minI + 1) / 2;
            bestRotation2[1] = 2;
        }
        if (bestRotation1[0] == 10 && bestRotation2[0] == 10) {//random rotation if no good rotation was found
            Random random = new Random();
            bestRotation1[0] = random.nextInt(4);
            bestRotation1[1] = random.nextInt(2);
            return bestRotation1;
        } else if (bestRotation1[0] == 10) {/*this 2 else if is despite the priority and just return the rotation*/
            return bestRotation2;
        } else if (bestRotation2[0] == 10) {
            return bestRotation1;
        } else {//this else is for situation when 2 rotation is find and we chose one base on priority
            if (typeOfPlay == 2)
                return bestRotation2;//defensive
            else
                return bestRotation1;//offensive
        }
    }
}
