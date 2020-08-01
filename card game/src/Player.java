package com.company;

import com.company.Card;
import com.company.Color;
import com.company.ColouredCard;
/**
 * this class is for informations of each player such
 * as cards he has ,number of player and the game he participate in
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Card> cards;
    int numberOfPlayer;
    Game game;

    /**
     * @param numberOfPlayer number of player in game
     * @param game           the game he participate in
     */
    public Player(int numberOfPlayer, Game game) {
        cards = new ArrayList<Card>();
        this.numberOfPlayer = numberOfPlayer;
        this.game = game;
    }

    /**
     * @return cards of player
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * @param card the card we want to add it to players card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * @return score of each player that is sum of cards point
     */
    public int getScore() {
        int score = 0;
        for (Card temp : cards) {
            if (temp instanceof WildCard)
                score += 50;
            else if (temp instanceof NormalCard) {
                ColouredCard temp2 = (ColouredCard) temp;
                score += temp2.getValue();
            } else if (temp instanceof MoveCard) {
                score += 20;
            }
        }
        return score;
    }

    /**
     * print deck of player
     */
    public void printCards() {
        System.out.println("NUMBER OF CARDS : " + cards.size());
        int numberOfCards = cards.size();
        int overFlow = 0;
        int rounds = 1;
        int[] start = new int[2];
        int[] end = new int[2];
        start[0] = 0;
        end[0] = cards.size();
        if (numberOfCards > 10) {
            start[1] = 10;
            end[0] = 10;
            end[1] = cards.size();
            numberOfCards = 10;
            rounds++;

        }
        char sign='x';
        for (int k = 0; k < rounds; k++) {
            for (int i = 0; i < 7; i++) {
                for (int j = start[k]; j < end[k]; j++) {
                    if (cards.get(j) instanceof WildCard)
                        System.out.print(Color.BLACK_BOLD_BRIGHT);
                    else if (cards.get(j) instanceof ColouredCard) {
                        ColouredCard temp2 = (ColouredCard) cards.get(j);
                        switch (temp2.getColor()){
                            case "blue":
                                sign='\u25C6';
                                break;
                            case "red":
                                sign='\u2665';
                                break;
                            case "yellow":
                                sign='\u2660';
                                break;
                            default:
                                sign='\u2663';
                                break;
                        }
                        temp2.showColor();
                    }
                    if (i == 0 || i == 6)
                        System.out.print("    #############     ");
                    else if (i == 3)
                        System.out.print("    # " + cards.get(j).getLabel() + " #     ");
                    else if (i==1)
                        System.out.print("    #  "+sign+"        #     ");
                    else if(i==5)
                        System.out.print("    #        "+sign+"  #     ");
                    else {
                        System.out.print("    #           #     ");
                    }
                }
                System.out.println(Color.RESET);
            }
        }
    }

    /**
     * this method compares card color and value and
     * finaly the conditaion for playing black cards
     *
     * @param card the card we want to check is valid or not
     * @return true if is valid
     */
    public boolean checkValidCard(Card card) {
        String color = "";//color of middle card
        int value = 0;//value of middle card
        if (game.getMiddleCard() instanceof ColouredCard) {
            ColouredCard current = (ColouredCard) game.getMiddleCard();
            color = current.getColor();
            value = current.getValue();
        } else {
            color = game.getColor();
            value = 50;
        }
        if (card instanceof ColouredCard) {
            ColouredCard temp = (ColouredCard) card;
            if (temp.getColor().equals(color) || temp.getValue() == value)
                return true;
        } else {//if chosen card was black card we check all of other cards and if find one valid card except black chosen card we return false
            for (Card temp : cards) {
                if (temp instanceof ColouredCard) {
                    ColouredCard temp2 = (ColouredCard) temp;
                    if (temp2.getValue() == value || temp2.getColor().equals(color))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * it check all of cards and call valid card for each one and if find
     * one valid card return true
     *
     * @return false if no valid card found
     */
    public boolean checkPass() {
        Random random = new Random();
        for (Card temp : cards) {
            if (checkValidCard(temp))
                return true;
        }
        int pos = random.nextInt(game.getCards().size());
        Card temp = game.getCards().get(pos);
        cards.add(temp);//add a new card to player cards
        game.getCards().remove(temp);
        for (Card temp1 : cards) {//check if new card is valid to put or nor
            if (checkValidCard(temp1)) {
                System.out.println("CARDS AFTER ADDING ONE RANDOM CARD : ");
                printCards();
                return true;
            }
        }
        return false;
    }

    /**
     * this method get a card , check it and the put it if it was valid to be played other wise keep getting card
     *
     * @return value of putted card
     */
    public int putCard() {
        System.out.println("PLEAS ENTER NUMBER OF CARD YOU WANT TO PUT ( FIRST CARD FROM LEFT IS NUMBER 1 )  : ");
        Card chosen = cards.get(scanner.nextInt() - 1);
        if (checkValidCard(chosen)) {//check if chosen card is valid or not
        } else {
            boolean flag = true;
            while (flag) {//continue getting next card to get a valid card
                System.out.println("ILLEGAL MOVE .");
                System.out.println("PLEAS ENTER NUMBER OF ANOTHER CARD TO PUT   : ");
                chosen = cards.get(scanner.nextInt() - 1);
                if (checkValidCard(chosen)) {
                    flag = false;
                }
            }
        }
        //putting card in middle and remove it from player cards
        cards.remove(chosen);
        game.getCards().add(game.getMiddleCard());
        game.setMiddleCard(chosen);
        System.out.println("PLAYER NUMBER " + (numberOfPlayer + 1) + "CHOSEN CARD :");//printing played card
        for (int i = 0; i < 7; i++) {
            if (chosen instanceof WildCard)
                System.out.print(Color.BLACK_BOLD_BRIGHT);
            else if (chosen instanceof ColouredCard) {
                ColouredCard temp2 = (ColouredCard) chosen;
                temp2.showColor();
            }
            if (i == 0 || i == 6)
                System.out.print("    #############     ");
            else if (i == 3)
                System.out.print("    # " + chosen.getLabel() + " #     ");
            else
                System.out.print("    #           #     ");
            System.out.println(Color.RESET);
        }
        return chosen.getValue();//return value of card to be used after to find out type of card which is played
    }

    /**
     * @param draw number of draw +2 or +4
     * @return true if a draw card has been find
     */
    public boolean searchForDraw(int draw) {
        for (Card card : cards) {
            if (card.getValue() == draw)
                return true;
        }
        return false;
    }

    /**
     * for each player it search cards and if a needed draw card found we pass and do nothing bot if a draw card has not been found
     * we add cards in number of draw to player cards and return false
     *
     * @param draw    number of draw +2 or +4
     * @param penalty number of penalty for example penalty 1 means 1 * draw card
     * @return true if draw card has not been undo
     */
    public boolean undoDraw(int draw, int penalty) {
        boolean flag = true;
        if (searchForDraw(draw)) {
            while (flag) {
                System.out.println("YOU HAVE A DRAW CARD . PUT THAT PLEAS TO UNDO DRAW EFFECT !!");
                int pos = scanner.nextInt();
                if (cards.get(pos - 1).getValue() == draw) {
                    cards.remove(cards.get(pos - 1));
                    game.getCards().add(game.getMiddleCard());
                    game.setMiddleCard(cards.get(pos - 1));
                    System.out.println("PLAYER NUMBER " + (numberOfPlayer + 1) + "CHOSEN CARD : ");
                    for (int i = 0; i < 7; i++) {
                        if (cards.get(pos - 1) instanceof WildCard)
                            System.out.print(Color.BLACK_BOLD_BRIGHT);
                        else if (cards.get(pos - 1) instanceof ColouredCard) {
                            ColouredCard temp2 = (ColouredCard) cards.get(pos - 1);
                            temp2.showColor();
                        }
                        if (i == 0 || i == 6)
                            System.out.print("    #############     ");
                        else if (i == 3)
                            System.out.print("    # " + cards.get(pos - 1).getLabel() + " #     ");
                        else
                            System.out.print("    #           #     ");
                        System.out.println(Color.RESET);
                    }
                    return true;
                }
            }
        }
        drawMove(draw, penalty);
        System.out.println("AFTER DRAW EFFECT !!!");
        return false;
    }

    /**
     * this method randomly add penalty * draw number of cards to player cards
     *
     * @param draw    number of draw +2 or +4
     * @param penalty number of times that draw card has been passed
     */
    public void drawMove(int draw, int penalty) {
        Random random = new Random();
        int numberOfCards = 0;
        if (draw == 40)
            numberOfCards = 2 * penalty;
        if (draw == 60)
            numberOfCards = 4 * penalty;
        for (int i = 0; i < numberOfCards; i++) {
            int pos = random.nextInt(game.getCards().size());
            Card temp = game.getCards().get(pos);
            game.removeCard(temp);
            cards.add(temp);
        }
    }

}