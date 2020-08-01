package com.company;
/**
 * this class holds nuts and manage them
 * it also have methods to creat , update and print visual map based on nuts and their location
 * it also have methods to find out if a spot is valid for putting  nut or not
 *
 * @author ARMIN GODARZI
 */

import com.company.Color;

import java.util.ArrayList;

public class PlayGround {
    private String[][] map;//this is visual map
    private ArrayList<Nut> nuts;//this a list of all nuts despite their number (color)
    private Nut[][] mapOfNuts;//an array of our nuts due to the index of each them we have location of them based on i and j

    /**
     * constructor
     * we make a visual map , a map for nuts to have their location base i and j ,a array list of all nuts which are in playground
     */
    public PlayGround() {
        map = new String[17][33];
        nuts = new ArrayList<Nut>();
        mapOfNuts = new Nut[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mapOfNuts[i][j] = new Nut(3, "empty");//empty spots in map of nuts are full of nuts which their number is 3
            }
        }
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (i % 2 == 0) {
                    if (j != 16)
                        map[i][j] = "---";
                    else
                        map[i][j] = "-";
                } else if (j % 2 == 0)
                    map[i][j] = "|";
                else
                    map[i][j] = "     ";
            }
        }
    }

    /**
     * this method first update map of nuts based on checking array list of nuts and location of each of them
     * then method updates visual map due to changes in map of nuts like adding nut or changing their color
     */
    public void updateMap() {
        for (Nut temp : nuts) {//check all of nuts
            int i = getI(temp.getLocation());
            int index = getJ(temp.getLocation()) + 1;
            mapOfNuts[i - 1][index - 1] = temp;//changing map of nuts and adding new nuts to it
            //updating visual map base on location and number of each nut
            if (temp.getNumberOfPlayer() == 1) {
                map[2 * i - 1][2 * index - 1] = "  B  ";
                ///map[2 * i - 1][2 * index - 1] = map[2 * i - 1][2 * index - 1].replace('B', '\u26AB');
            } else {
                map[2 * i - 1][2 * index - 1] = "  W  ";
                //map[2 * i - 1][2 * index - 1] = map[2 * i - 1][2 * index - 1].replace('W', '\u26AA');
            }
        }
    }

    /**
     * @param nut nut to add in list of nuts
     */
    public void addNut(Nut nut) {
        nuts.add(nut);
    }

    /**
     * just a method that first update visual map then print it
     */
    public void printMap() {
        updateMap();
        int counter = 1;
        System.out.print(Color.YELLOW_BACKGROUND_BRIGHT);
        System.out.print(Color.RED_BOLD_BRIGHT);
        System.out.println("                                                                              A     B     C     D     E     F     G     H");
        System.out.print(Color.BLACK_BOLD_BRIGHT);
        for (int i = 0; i < 17; i++) {
            if (counter % 2 == 0) {
                System.out.print(Color.RED_BOLD_BRIGHT);
                System.out.print("                                                                        " + counter / 2 + "  ");
                System.out.print(Color.BLACK_BOLD_BRIGHT);
            } else
                System.out.print("                                                                        " + " " + "  ");
            counter++;
            System.out.print(Color.BLUE_BOLD_BRIGHT);
            for (int j = 0; j < 17; j++) {
                if (map[i][j].equals("  W  ")) {
                    System.out.print(Color.WHITE_BOLD_BRIGHT);
                    System.out.print(map[i][j]);
                    System.out.print(Color.BLUE_BOLD_BRIGHT);
                } else if (map[i][j].equals("  B  ")) {
                    System.out.print(Color.BLACK_BOLD_BRIGHT);
                    System.out.print(map[i][j]);
                    System.out.print(Color.BLUE_BOLD_BRIGHT);
                } else
                    System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.print(Color.BLACK_BOLD_BRIGHT);
    }

    /**
     * a method to check if there is a nut in given location or nut
     *
     * @param location location we want to check
     * @return true or false
     */
    public boolean checkEmpty(String location) {
        for (Nut temp : nuts) {
            if (temp.getLocation().equals(location))
                return false;
        }
        return true;
    }

    /**
     * this method this method first get i and j based on location
     * then due to 8 direction around this location decide it is valid or not
     *
     * @param location       location we want to check
     * @param numberOfPlayer number of player
     * @return true if location was valid
     */
    public boolean checkLegality(String location, int numberOfPlayer) {
        int i = getI(location) - 1;
        int j = getJ(location);
        if (i >= 0 && j >= 0) {
            if (j + 1 <= 7 && (mapOfNuts[i][j + 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//right
                return true;
            else if (j - 1 >= 0 && (mapOfNuts[i][j - 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//left
                return true;
            else if (i + 1 <= 7 && (mapOfNuts[i + 1][j].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//down
                return true;
            else if (i - 1 >= 0 && (mapOfNuts[i - 1][j].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//up
                return true;
            else if (i - 1 >= 0 && j - 1 >= 0 && (mapOfNuts[i - 1][j - 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//north_west
                return true;
            else if (i - 1 >= 0 && j + 1 <= 7 && (mapOfNuts[i - 1][j + 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//north_east
                return true;
            else if (i + 1 <= 7 && j + 1 <= 7 && (mapOfNuts[i + 1][j + 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//south_east
                return true;
            else if (i + 1 <= 7 && j - 1 >= 0 && (mapOfNuts[i + 1][j - 1].getNumberOfPlayer() == (1 + numberOfPlayer % 2)))//south+west
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * this method move to all 8 direction from location of new nut and
     * due to rolls of game change color of nuts (actually change their number)
     * and also keep number of nuts that new nut changed their color in a variable named totalChanges
     * more information on first while loop for moving to left .
     *
     * @param location       of new nut
     * @param numberOfPlayer number of player
     * @return number of nuts that new nut changes their color
     */
    public int changeNuts(String location, int numberOfPlayer) {
        updateMap();
        int i = getI(location) - 1;
        int j = getJ(location);
        if (i >= 0 && j >= 0) {
            /*flag is a variable to stop moving and it also determine why moving has been stop 2 for empty spots and 0 for spots full of nuts
            whit same number of player*/
            /*counter is a variable that  increasing it will cause to move more  */
            /*length is number of spots full of nuts with different number of player */
            int flag = 1, counter = 1, length = 0, totalChange = 0;
            while (flag == 1 && j - counter >= 0) {
                if (mapOfNuts[i][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {//conditions for changing color
                totalChange += length;
                for (int k = 1; k <= length; k++) {//for is nedded to change all the nuts between starting location and the end
                    mapOfNuts[i][j - k].setNumberOfPlayer(numberOfPlayer);//changing nut number of player
                }
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && j + counter <= 7) {
                if (mapOfNuts[i][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i][j + k].setNumberOfPlayer(numberOfPlayer);
                }
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0) {
                if (mapOfNuts[i - counter][j].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i - k][j].setNumberOfPlayer(numberOfPlayer);
                }
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7) {
                if (mapOfNuts[i + counter][j].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i + k][j].setNumberOfPlayer(numberOfPlayer);
                }
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7 && j - counter >= 0) {
                if (mapOfNuts[i + counter][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i + k][j - k].setNumberOfPlayer(numberOfPlayer);
                }
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0 && j + counter <= 7) {
                if (mapOfNuts[i - counter][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }

            if (length > 0 && flag == 0) {
                totalChange += length;
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i - k][j + k].setNumberOfPlayer(numberOfPlayer);
                }
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7 && j + counter <= 7) {
                if (mapOfNuts[i + counter][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i + k][j + k].setNumberOfPlayer(numberOfPlayer);
                }
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0 && j - counter >= 0) {
                if (mapOfNuts[i - counter][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
                for (int k = 1; k <= length; k++) {
                    mapOfNuts[i - k][j - k].setNumberOfPlayer(numberOfPlayer);
                }
            }
            return totalChange;
        }
        return 0;
    }

    /**
     * @return list of nuts
     */
    public ArrayList<Nut> getNuts() {
        return nuts;
    }

    /**
     * @param location we want to get its j
     * @return j base on location in playground
     */
    public int getJ(String location) {
        String letters = "ABCDEFGH";
        return letters.indexOf(location.charAt(1));
    }

    /**
     * @param location we want its j
     * @return i base on location in playground
     */
    public int getI(String location) {
        char x = location.charAt(0);
        switch (x) {
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            default:
                return 0;
        }
    }

    /**
     * @return map of nuts
     */
    public Nut[][] getMapOfNuts() {
        return mapOfNuts;
    }

    /**
     * @param i i of location
     * @param j j of location
     * @return a location in play ground base on given i and j
     */
    public String getLocation(int i, int j) {
        String location = "";
        switch (i) {
            case 1:
                location = "1X";
                break;
            case 2:
                location = "2X";
                break;
            case 3:
                location = "3X";
                break;
            case 4:
                location = "4X";
                break;
            case 5:
                location = "5X";
                break;
            case 6:
                location = "6X";
                break;
            case 7:
                location = "7X";
                break;
            case 8:
                location = "8X";
                break;
            default:
                location = "0X";
        }
        String letters = "ABCDEFGH";
        return location.replace('X', letters.charAt(j));
    }

    /**
     * this method is exactly the changeNuts method and works like it but only difference is in this method
     * we do not change the color we just determine how many changes will happen
     * output of this method will help us with checking a spot is valid or not
     *
     * @param location       we want to check
     * @param numberOfPlayer number of player
     * @return number of nuts that putting nut in location will change their color
     */
    public int checkPassEachSpot(String location, int numberOfPlayer) {
        updateMap();
        int i = getI(location) - 1;
        int j = getJ(location);
        int totalChange = 0;
        if (i >= 0 && j >= 0) {
            int flag = 1, counter = 1, length = 0;
            while (flag == 1 && j - counter >= 0) {
                if (mapOfNuts[i][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && j + counter <= 7) {
                if (mapOfNuts[i][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0) {
                if (mapOfNuts[i - counter][j].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7) {
                if (mapOfNuts[i + counter][j].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7 && j - counter >= 0) {
                if (mapOfNuts[i + counter][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0 && j + counter <= 7) {
                if (mapOfNuts[i - counter][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }

            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i + counter <= 7 && j + counter <= 7) {
                if (mapOfNuts[i + counter][j + counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i + counter][j + counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
            flag = 1;
            counter = 1;
            length = 0;
            while (flag == 1 && i - counter >= 0 && j - counter >= 0) {
                if (mapOfNuts[i - counter][j - counter].getNumberOfPlayer() == 3)
                    flag = 2;
                else if (mapOfNuts[i - counter][j - counter].getNumberOfPlayer() == numberOfPlayer)
                    flag = 0;
                else
                    length++;
                counter++;
            }
            if (length > 0 && flag == 0) {
                totalChange += length;
            }
        }
        return totalChange;

    }
}
