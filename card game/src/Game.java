package com.company;

import com.company.Color;
import com.company.ColouredCard;
import com.company.MoveCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * this class holds information of a game and have methods to show game status
 * giving and setting information and crating deck and shuffle cards
 */
public class Game {
    Random random = new Random();
    private ArrayList<Card> cards;//list of cards in game
    private ArrayList<Player> players;//list of players who participate in game
    private Card middleCard;// the card which is in the middle of game
    private int direction;//antiClockWise 2 and clockWise 1
    private int numberOfPlayers;//number of players of game
    private String color;//color of game if middle card was black

    /**
     * @param numberOfPlayers number of players in game
     */
    public Game(int numberOfPlayers) {
        cards = new ArrayList<Card>();
        players = new ArrayList<Player>();
        direction = 1;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * @param player player we want to add it in game
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * give 7 random cards to each player
     */
    public void shuffle() {
        int x = (players.size() / 10) + 1;
        for (int i = 0; i < x; i++) {
            creatDeck();
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                int pos = random.nextInt(cards.size()-1);
                if (i == 0 && cards.get(pos).getValue() == 40)
                    pos += 10;
                players.get(i).addCard(cards.get(pos));
                cards.remove(cards.get(pos));
            }
        }
    }

    /**
     * show cards of each player
     */
    public void showDecks() {
        for (Player temp : players) {
            temp.printCards();
        }
    }

    /**
     * @param typeOfPlay type of play which cause different type of map to be printed
     * @param turn       turn of player to print his deck
     */
    public void showPlayGround(int typeOfPlay, int turn) {
        if (typeOfPlay == 2) {
            if (numberOfPlayers == 4) {
                System.out.println(" |                                                                                 PLAYER 2");
                System.out.println(" |                                                                               #############");
                for (int i = 0; i < 5; i++) {
                    if (i == 2)
                        System.out.println(" |                                                                               #     " + players.get(1).getCards().size() + "     #");
                    else
                        System.out.println(" |                                                                               #           #");
                }
                System.out.print(" |                                                                               #############");
                System.out.println("\n |");
            } else {
                System.out.println(" |                                                   PLAYER 2                                                      PLAYER 3");
                System.out.println(" |                                                 #############                                                 #############");
                for (int i = 0; i < 5; i++) {
                    if (i == 2)
                        System.out.println(" |                                                 #     " + players.get(1).getCards().size() + "     #" + "                                                 #     " + players.get(2).getCards().size() + "     #");
                    else
                        System.out.println(" |                                                 #           #                                                 #           #");
                }
                System.out.println(" |                                                 #############                                                 #############");
            }
            if (middleCard instanceof ColouredCard) {
                ColouredCard temp = (ColouredCard) middleCard;
                temp.showColor();
            }
            System.out.println(" |                                                                               <MIDDLE CARD>");
            System.out.println(" |                                                                               #############");
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    if (middleCard.getValue() == 50 || middleCard.getValue() == 60) {
                        System.out.print(" |                                                                               # " + middleCard.getLabel() + " #");
                        System.out.println(" |                 COLOR : " + color);
                    } else
                        System.out.println(" |                                                                               # " + middleCard.getLabel() + " #");

                } else
                    System.out.println(" |                                                                               #           #");
            }
            System.out.println(" |                                                                               #############");
            System.out.print(Color.RESET);
            System.out.println(" |                                                  PLAYER " + (numberOfPlayers) + "                                                       PLAYER " + (numberOfPlayers - 1));
            System.out.println(" |                                                #############                                                 #############");
            for (int i = 0; i < 5; i++) {
                if (i == 2)
                    System.out.println(" |                                                #     " + players.get(numberOfPlayers - 1).getCards().size() + "     #" + "                                                 #     " + players.get(numberOfPlayers - 2).getCards().size() + "     #");
                else
                    System.out.println(" |                                                #           #                                                 #           #");
            }
            System.out.println(" |                                                #############                                                 #############");
            System.out.println();
            players.get(0).printCards();
        } else {
            for (Player player : players) {
                System.out.println(Color.PURPLE+"------>"+Color.YELLOW_BRIGHT  +"PLAYER NUMBER " + (player.numberOfPlayer + 1) + "  HAS  " + player.getCards().size() + " CARDS .");
            }
            if (middleCard instanceof ColouredCard) {
                ColouredCard temp = (ColouredCard) middleCard;
                temp.showColor();
            }
            System.out.println("                                                                            <MIDDLE CARD>");
            System.out.println("                                                                            #############");
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    if (middleCard.getValue() == 50 || middleCard.getValue() == 60) {
                        System.out.print("                                                                            # " + middleCard.getLabel() + " #");
                        System.out.println("              COLOR : " + color);
                    } else
                        System.out.println("                                                                            # " + middleCard.getLabel() + " #");

                } else
                    System.out.println("                                                                            #           #");
            }
            System.out.println("                                                                            #############");
            System.out.println("----> YOUR DECK : ");
            players.get(turn).printCards();
        }
    }

    /**
     * @return list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * creat 108 card
     */
    public void creatDeck() {
        String[] color = {"red", "green", "yellow", "blue"};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0)
                    cards.add(new NormalCard(j, color[i]));
                else {
                    cards.add(new NormalCard(j, color[i]));
                    cards.add(new NormalCard(j, color[i]));
                }
            }
            cards.add(new MoveCard(20, color[i], "Skip"));
            cards.add(new MoveCard(20, color[i], "Skip"));
            cards.add(new MoveCard(30, color[i], "Reverse"));
            cards.add(new MoveCard(30, color[i], "Reverse"));
            cards.add(new MoveCard(40, color[i], "Draw2"));
            cards.add(new MoveCard(40, color[i], "Draw2"));
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(50, 1));
            cards.add(new WildCard(60, 2));
        }
    }

    /**
     * @return current middle card
     */
    public Card getMiddleCard() {
        return middleCard;
    }

    /**
     * @param middleCard set new middle card
     */
    public void setMiddleCard(Card middleCard) {
        this.middleCard = middleCard;
    }

    /**
     * set a random coloured card as first middle cart to start the game
     *
     * @return value of first middle card
     */
    public int setFirstMiddleCard() {
        boolean flag = true;
        int output = 0;
        while (flag) {
            int pos = random.nextInt(cards.size());
            Card temp = cards.get(pos);
            if (temp instanceof ColouredCard) {
                middleCard = temp;
                cards.remove(temp);
                flag = false;
                output = temp.getValue();
                if (temp.getValue() == 30)
                    direction = 2;
                else if (temp.getValue() == 40) {
                    for (int i = 0; i < 2; i++) {
                        int index = random.nextInt(cards.size());
                        Card temp2 = cards.get(index);
                        removeCard(temp2);
                        Player current = players.get(0);
                        current.addCard(temp2);
                    }
                }
            }
        }
        return output;
    }

    /**
     * @return current direction of round
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction set the new direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return color of middle card if it was black
     */
    public String getColor() {
        return color;
    }

    /**
     * @param card the card we want to remove it
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * @return list of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * @param color new color for black card in middle
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return true a player had no card
     */
    public boolean checkEnd() {
        for (Player player : players) {
            if (player.getCards().size() == 0)
                return true;
        }
        return false;
    }

    /**
     * print all players with their number of cards and the winner of the game
     */
    public void printStatus() {
        int counter = 2;
        for (Player player : players) {
            if (player.getCards().size() == 0)
                counter = player.numberOfPlayer;
        }
        System.out.println(Color.RED_BOLD + "                                                                                                            THE WINNER IS PLAYER NUMBER  " + (counter + 1));
        System.out.println(Color.BLUE_BOLD + "                                                                                                            LIST OF PLAYERS : ");
        System.out.print(Color.RESET);
        for (Player player : players) {
            if (player.numberOfPlayer == counter)
                System.out.print(Color.GREEN_BOLD);
            for (int i = 0; i < 3; i++) {
                if (i == 1)
                    System.out.println("                                                                                      |  NUMBER OF PLAYER  :  " + (player.numberOfPlayer + 1) + "   NUMBER OF CARDS : " + player.getCards().size() + " TOTAL POINTS : " + player.getScore() + "  |");
                else
                    System.out.println("                                                                                       __________________________________________________________________");

            }
            System.out.print(Color.RESET);
        }
    }
}
