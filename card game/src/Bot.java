package com.company;

import com.company.Card;

/**
 * this class is subclass for player and have the same informations and methods the only difference is
 * bot class chose cards it self in put card and draw method
 */
public class Bot extends Player {
    /**
     * @param numberOfPlayer number of player in game
     * @param game           the game bot participate in
     */
    public Bot(int numberOfPlayer, Game game) {
        super(numberOfPlayer, game);
    }

    /**
     * this method find a valid card and then put it in game just like player class
     *
     * @return value of putted card
     */
    public int putCard() {
        Card chosen = new Card(100);
        for (Card card : cards) {
            if (checkValidCard(card)) {
                chosen = card;
                break;
            }
        }
        cards.remove(chosen);
        game.getCards().add(game.getMiddleCard());
        game.setMiddleCard(chosen);
        System.out.println("PLAYER NUMBER " + (numberOfPlayer + 1) + "CHOSEN CARD :");
        for (int i = 0; i < 7; i++) {
            if (chosen instanceof WildCard)
                System.out.print(com.company.Color.BLACK_BOLD_BRIGHT);
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
        return chosen.getValue();
    }

    /**
     * this method like put card method work just like undoDraw method in player class
     * only difference is that the method itself chose a draw card if it has been found
     *
     * @param draw    +2 or +4
     * @param penalty number of penalty
     * @return false if draw has been undo
     */
    public boolean undoDraw(int draw, int penalty) {
        boolean flag = true;
        if (searchForDraw(draw)) {
            while (flag) {
                Card chosen = new Card(100);
                for (Card card : cards) {
                    if (card.getValue() == draw)
                        chosen = card;
                }
                cards.remove(chosen);
                game.getCards().add(game.getMiddleCard());
                game.setMiddleCard(chosen);
                System.out.println("PASSING DRAW BY PLAYER NUMBER " + (numberOfPlayer + 1) + "CHOSEN CARD :");
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
                return true;
            }
        }
        drawMove(draw, penalty);
        System.out.println("AFTER DRAW EFFECT  : ");
        game.showPlayGround(2, 0);
        return false;
    }

}
