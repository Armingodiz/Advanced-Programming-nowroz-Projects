package com.company;
/**
 * this class creat and update a map and create , update and print a visual map
 * base on 4 parts maps .
 * this class also have methods to add marble to play ground , rotate parts and check end game .
 *
 * @author ARMIN GODARZI
 */

import com.company.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class PlayGround {
    private Part part1;
    private Part part2;
    private Part part3;
    private Part part4;
    private int[][] map;// a 6 * 6 map for marbles and empty spots .
    private String[][] visualMap;// a visual map created and updated based on map

    /**
     * constructor
     * also give value of 0 to all empty spots of map
     */
    public PlayGround() {
        part1 = new Part(1);
        part2 = new Part(2);
        part3 = new Part(3);
        part4 = new Part(4);
        map = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                map[i][j] = 0;
            }
        }
        visualMap = new String[13][13];
    }

    /**
     * create an empty visual map
     */
    public void createMap() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                if (i % 2 == 0) {
                    if (j <= 6)
                        if (j == 4)
                            visualMap[i][j] = "---";
                        else

                            visualMap[i][j] = "----";
                    else if (j <= 11)
                        visualMap[i][j] = "--";
                    else
                        visualMap[i][j] = "-";
                } else if (j % 2 == 0)
                    visualMap[i][j] = "|";
                else
                    visualMap[i][j] = "     ";
            }
        }

    }

    /**
     * update visual map and put players sign base on map changes
     */
    public void updateVisualMap() {
        updateMap();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] == 1)
                    visualMap[2 * i + 1][2 * j + 1] = "  B  ";
                else if (map[i][j] == 2)
                    visualMap[2 * i + 1][2 * j + 1] = "  R  ";
                else if (map[i][j] == 0)
                    visualMap[2 * i + 1][2 * j + 1] = "     ";
            }
        }

    }

    /**
     * update map based on changes on map of 4 parts
     */
    public void updateMap() {
        for (int i = 0; i < 6; i++) {//we will empty the map to fill it again base on changes
            for (int j = 0; j < 6; j++) {
                map[i][j] = 0;
            }
        }
        int[][] map1 = part1.getMap();
        int[][] map2 = part2.getMap();
        int[][] map3 = part3.getMap();
        int[][] map4 = part4.getMap();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (map1[i][j] > 0)
                    map[i][j] = map1[i][j];
                if (map2[i][j] > 0)
                    map[i][j + 3] = map2[i][j];
                if (map3[i][j] > 0)
                    map[i + 3][j] = map3[i][j];
                if (map4[i][j] > 0)
                    map[i + 3][j + 3] = map4[i][j];
            }
        }
    }

    /**
     * print visual map
     */
    public void printMap() {
        updateVisualMap();
        int counter = 0;
        System.out.print(Color.WHITE_BACKGROUND);
        System.out.print(Color.YELLOW_BOLD);
        System.out.println("                                                                 0     1     2          3     4     5       ");
        System.out.print(Color.RESET);
        System.out.print(Color.WHITE_BACKGROUND);
        System.out.print(Color.BLACK_BOLD);
        for (int i = 0; i < 13; i++) {
            System.out.print("                                                            ");
            if (i == 6) {
                System.out.println("  -------------------    -------------------");
                System.out.print("                                                            ");
            }
            if (i % 2 != 0) {
                System.out.print(Color.YELLOW_BOLD_BRIGHT);
                System.out.print(counter + " ");
                System.out.print(Color.BLACK_BOLD);
                counter++;
            } else {
                System.out.print("  ");
            }
            for (int j = 0; j < 13; j++) {
                if (j == 7 && i % 2 != 0) {
                    System.out.print("    |");
                } else if (j == 5 && i % 2 == 0) {
                    System.out.print("    ");
                }
                if (visualMap[i][j].equals("  R  ")) {
                    System.out.print(Color.RED_BOLD_BRIGHT);
                    System.out.print(visualMap[i][j]);
                    System.out.print(Color.BLACK_BOLD);
                } else if (visualMap[i][j].equals("  B  ")) {
                    System.out.print(Color.BLUE_BOLD_BRIGHT);
                    System.out.print(visualMap[i][j]);
                    System.out.print(Color.BLACK_BOLD);
                } else
                    System.out.print(visualMap[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * this method will chang the x and y
     * then with new x and y add marble
     * to a part chosen base on old x and y
     * adding marble to a spot means changing that spot value
     * in map of that part.
     *
     * @param numberOfPlayer number of player of the marble we want to add it
     * @param x              x of location
     * @param y              y of location
     */
    public void addMarble(int numberOfPlayer, int x, int y) {
        boolean flag = true;
        int j = x;
        int i = y;
        if (map[i][j] == 0) {
            if (i <= 2 && j <= 2) {//part1
                part1.setMap(i, j, numberOfPlayer);//adding marble
            } else if (i <= 2 && j > 2) {//part2
                part2.setMap(i, j - 3, numberOfPlayer);//adding marble
            } else if (i > 2 && j <= 2) {//part3
                part3.setMap(i - 3, j, numberOfPlayer);//adding marble
            } else if (i > 2 && j > 2) {//part4
                part4.setMap(i - 3, j - 3, numberOfPlayer);//adding marble
            }
        } else {
            while (flag) {//this else is for situation which chosen spot is full we continue to give new x and y from user while find a empty spot
                Scanner myscanner = new Scanner(System.in);
                System.out.println("PLEAS ENTER ANOTHER LOCATION BECAUSE THIS LOCATION IS FULL");
                System.out.println("PLEAS ENTER X AND Y : ");
                j = myscanner.nextInt();
                i = myscanner.nextInt();
                if (map[i][j] == 0) {
                    if (i <= 2 && j <= 2) {//part1
                        part1.setMap(i, j, numberOfPlayer);
                    } else if (i <= 2 && j > 2) {//part2
                        part2.setMap(i, j - 3, numberOfPlayer);
                    } else if (i > 2 && j <= 2) {//part3
                        part3.setMap(i - 3, j, numberOfPlayer);
                    } else if (i > 2 && j > 2) {//part4
                        part4.setMap(i - 3, j - 3, numberOfPlayer);
                    }
                    flag = false;
                }
            }
        }
    }

    /**
     * this method rotate map of chosen part using rotate method in part class
     * each rotate cause map to rotate 45 degree so for round clock rotation we must rotate 2 times
     * and for clock wise rotation we must rotate 8 times
     *
     * @param numberOfPart number of part we want to rotate it
     * @param direction    direction we want to rotate part
     */
    public void rotatePart(int numberOfPart, int direction) {
        switch (numberOfPart) {
            case 1:
                if (direction == 1) {
                    for (int i = 0; i < 2; i++)
                        part1.rotate();
                } else if (direction == 2)
                    for (int i = 0; i < 6; i++)
                        part1.rotate();
                break;
            case 2:
                if (direction == 1) {
                    for (int i = 0; i < 2; i++)
                        part2.rotate();
                } else if (direction == 2)
                    for (int i = 0; i < 6; i++)
                        part2.rotate();
                break;
            case 3:
                if (direction == 1) {
                    for (int i = 0; i < 2; i++)
                        part3.rotate();
                } else if (direction == 2)
                    for (int i = 0; i < 6; i++)
                        part3.rotate();
                break;
            case 4:
                if (direction == 1) {
                    for (int i = 0; i < 2; i++)
                        part4.rotate();
                } else if (direction == 2)
                    for (int i = 0; i < 6; i++)
                        part4.rotate();
                break;
        }
    }

    /**
     * this method check all full spots and check if they make a trial with 4 other spot with the same value(number of player) or not .
     *
     * @return number of player of spots which are starting point or ending point of a trial
     */
    public int checkWin() {
        updateMap();
        int winner = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (map[i][j] > 0)
                    if (findTrail(i, j, map[i][j]) >= 4)
                        winner += map[i][j];
            }
        }
        return winner;
    }

    /**
     * this method move to 8 possible direction from chosen spot and find and return longest trial of marbles with the a=same number of player
     * more information on first while which is for going up
     *
     * @param i              i of location
     * @param j              j of location
     * @param numberOfPlayer number of player we want to find a trial of it
     * @return max length of trial that chosen location is a starting or ending point for it .
     */
    public int findTrail(int i, int j, int numberOfPlayer) {
        int counter = 0, surveyor = 1, flag = 1, max = 0;
        /*counter is number of marbles with same number of player which make a trial with chosen marble*/
        // surveyor is a variable that increasing it causes change in i or j or both be bigger
        //max is length of trial in this direction
        while (i - surveyor >= 0 && flag == 1) {
            if (map[i - surveyor][j] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (i + surveyor <= 5 && flag == 1) {
            if (map[i + surveyor][j] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j + surveyor <= 5 && flag == 1) {
            if (map[i][j + surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j - surveyor >= 0 && flag == 1) {
            if (map[i][j - surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j - surveyor >= 0 && i - surveyor >= 0 && flag == 1) {
            if (map[i - surveyor][j - surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j - surveyor >= 0 && i + surveyor <= 5 && flag == 1) {
            if (map[i + surveyor][j - surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j + surveyor <= 5 && i - surveyor >= 0 && flag == 1) {
            if (map[i - surveyor][j + surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        counter = 0;
        surveyor = 1;
        flag = 1;
        while (j + surveyor <= 5 && i + surveyor <= 5 && flag == 1) {
            if (map[i + surveyor][j + surveyor] == numberOfPlayer)
                counter++;
            else
                flag = 0;
            surveyor++;
        }
        if (counter > max)
            max = counter;
        return max;
    }

    /**
     * @return map of play ground
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * @return number of marbles which are in play ground
     */
    public int getCountOfMarbles() {
        int count = part1.countMarble() + part2.countMarble() + part3.countMarble() + part4.countMarble();
        return count;
    }
}

