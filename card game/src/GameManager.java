package com.company;
/**
 * this class is for playing and have methods to effect on game base on the card played in
 * each round .
 */

import com.company.*;
import com.company.Card;
import com.company.Color;
import com.company.ColouredCard;
import com.company.Game;
import com.company.Player;

import java.util.Random;
import java.util.Scanner;

public class GameManager {
    Scanner scanner = new Scanner(System.in);

    /**
     * method to declare mood of game
     */
    public void start() {
        System.out.println("PLEAS ENTER MOOD OF GAME : ");
        System.out.println("1 ) PLAY WITH n OTHER PLAYERS .");
        System.out.println("2 ) PLAY WITH BOTS .");
        int input = scanner.nextInt();
        play(input);
    }

    /**
     * this method first get information needed for game like number of players
     * then creat game and players and then start the game and continue playing
     * rounds until end of game
     *
     * @param typeOfPlay mood of game
     */
    public void play(int typeOfPlay) {
        int output = 1000;
        Game game = new Game(5);
        int numberOfPlayers = 0;
        if (typeOfPlay == 2) {
            System.out.println("PLEAS ENTER NUMBER OF BOTS : ");
            System.out.println("1 ) 3 BOTS .");
            System.out.println("2 ) 4 BOTS .");
            System.out.print(" ---> ");
            numberOfPlayers = scanner.nextInt() + 3;
            game = new Game(numberOfPlayers);
            game.addPlayer(new Player(0, game));
            for (int i = 1; i < numberOfPlayers; i++) {
                game.addPlayer(new Bot(i, game));//adding bots to game
            }
        } else {
            System.out.println("PLEAS ENTER NUMBER OF PLAYERS : ");
            System.out.print(" ---> ");
            numberOfPlayers = scanner.nextInt();
            game = new Game(numberOfPlayers);
            for (int i = 0; i < numberOfPlayers; i++) {
                game.addPlayer(new Player(i, game));//adding player to game
            }
        }
        int turn = 100 * numberOfPlayers;
        game.shuffle();
        if (game.setFirstMiddleCard() % 20 == 0)//move turn forward if first middle cart was  skip
            turn++;
        boolean flag = true;
        game.showPlayGround(typeOfPlay, turn % numberOfPlayers);//show play ground
        while (flag) {
            System.out.println("\n");
            System.out.println(Color.PURPLE + " ***********************************************************************************************************************************************************************************************");
            System.out.println(Color.RED_BOLD+"                                                                  DIRECTION : "+Color.GREEN_BOLD + getSignOfDirection(game.getDirection()) +Color.RED_BOLD +"       TURN : " +Color.GREEN_BOLD+ (turn % numberOfPlayers + 1)+"");
            System.out.println();
            System.out.print(Color.RESET);
            game.showPlayGround(typeOfPlay, turn % numberOfPlayers);
            System.out.println("PLAYER NUMBER " + (turn % numberOfPlayers + 1) + " IT IS YOUR TURN ! ");
            Player temp = game.getPlayers().get(turn % numberOfPlayers);
            if (temp.checkPass()) {//check if player player has a valid card in deck to put
                output = temp.putCard();//put card and put value of putted card in output variable
                impression(output, game, turn % numberOfPlayers, typeOfPlay);//change the game status with calling impression method
                if (output == 20) {
                    turn = getTurn(turn, game);//skip the turn
                }
                if (output == 40 || output == 60) {//skip the turn if a draw card has been putted
                    int penalty = 1;
                    turn = getTurn(turn, game);
                    Player current = game.getPlayers().get(turn % numberOfPlayers);
                    while (current.undoDraw(output, penalty)) {//keep moving to players if they put draw card on draw
                        impression(output, game, turn, 2);//this part for changing color if wild draw putted
                        turn = getTurn(turn, game);//get the turn
                        current = game.getPlayers().get(turn % numberOfPlayers);//move to next player
                        penalty++;//increasing penalty that cause next player has to take double card for draw
                    }
                }
            } else
                System.out.println("SORRY NO VALID MOVE !! YOU MUST TAKE A CARD !");
            game.showPlayGround(typeOfPlay, turn % numberOfPlayers);
            turn = getTurn(turn, game);//getting next turn
            if (game.checkEnd())
                flag = false;
        }
        game.printStatus();//after ending game prints all players number of left cards and the winner
    }


    public int getTurn(int turn, Game game) {//get current turn and give next turn base on direction of round
        if (game.getDirection() == 1)
            return turn + 1;
        else
            return turn - 1;
    }

    /**
     * this method do the changes based on value of putted card
     * and then do the changes to game and player
     *
     * @param value      value of card
     * @param game       game
     * @param nextTurn   turn of player (number of player in game)
     * @param typeOfGame type of game playing with bots or n other players
     */
    public void impression(int value, Game game, int nextTurn, int typeOfGame) {
        Random random = new Random();
        switch (value) {
            case 30://change direction of rounding
                game.setDirection(1 + game.getDirection() % 2);
                break;
            case 50://declare new color if black card had been chosen
                if (typeOfGame == 1 || nextTurn == 0) {//if turn was for player get the color with scanner
                    System.out.println("PLEAS ENTER NEW COLOR :");
                    game.setColor(scanner.next());
                } else {//if turn was for bot change color which has the most color in the same card
                    Player temp = game.getPlayers().get(nextTurn);
                    int flag = 1;
                    for (Card current : temp.getCards()) {
                        if (current instanceof ColouredCard) {
                            ColouredCard current2 = (ColouredCard) current;
                            game.setColor(current2.getColor());
                            flag = 0;
                            break;
                        }
                    }
                    if (flag == 1) {//chose a random color
                        String[] colors = {"blue", "red", "green", "yellow"};
                        game.setColor(colors[random.nextInt(3)]);
                    }
                }
                break;
            case 60:
                int x = 0;
                if (game.getDirection() == 1)
                    x = nextTurn - 1;
                else
                    x = nextTurn + 1;
                if (typeOfGame == 1 || x == 0) {
                    System.out.println("PLEAS ENTER NEW COLOR :");
                    game.setColor(scanner.next());
                } else {
                    Player temp = game.getPlayers().get(nextTurn);
                    int flag = 1;
                    for (Card current : temp.getCards()) {
                        if (current instanceof ColouredCard) {
                            ColouredCard current2 = (ColouredCard) current;
                            game.setColor(current2.getColor());
                            flag = 0;
                            break;
                        }
                    }
                    if (flag == 1) {
                        String[] colors = {"blue", "red", "green", "yellow"};
                        game.setColor(colors[random.nextInt(3)]);
                    }
                }
                break;
            default:
                break;
        }

    }

    public String getSignOfDirection(int direction) {
        if (direction == 1)
            return "clock wise  " + "\u21B7";
        else
            return "anti clock wise  " + "\u21B6";
    }
}