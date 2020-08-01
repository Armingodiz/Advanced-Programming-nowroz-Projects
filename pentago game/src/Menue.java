package com.company;

import java.util.Scanner;

public class Menue {
    public void start() {


        Scanner myscanner = new Scanner(System.in);
        PlayGround game = new PlayGround();
        PlayWithBot bot = new PlayWithBot(game);
        game.createMap();
        int turn = 1;
        boolean flag = true;
        System.out.println("PLEAS ENTER MOOD OF GAME : ");
        System.out.println("1 ) 1 VS 1 ");
        System.out.println("2 ) 1 VS BOT ");
        game.printMap();
        int input = myscanner.nextInt();
        switch (input) {
            case 1:
                while (flag) {
                    if (game.getCountOfMarbles() == 36)//check if map is full or not
                        System.out.println("GAME IS OVER !!             DRAW ! ");
                    System.out.println("TURN : PLAYER NUMBER " + (2 - turn % 2));
                    System.out.println("PLEAS ENTER X AND Y : ");
                    int x = myscanner.nextInt();
                    int y = myscanner.nextInt();
                    game.addMarble(2 - turn % 2, x, y);
                    game.printMap();
                    System.out.println("PLEAS ENTER NUMBER OF PART TO TURN :");
                    int numberOfPart = myscanner.nextInt();
                    System.out.println("PLEAS ENTER DIRECTION : ");
                    System.out.println("1 ) ROUND CLOCK . ");
                    System.out.println("2 ) COUNTER CLOCKWISE . ");
                    int direction = myscanner.nextInt();
                    game.rotatePart(numberOfPart, direction);
                    game.printMap();
                    int winner = game.checkWin();
                    if (winner > 10) {
                        System.out.println("DRAW ! ");
                        flag = false;
                    }
                    if (winner > 0 && winner < 10) {
                        if ((winner / 2) == 1)
                            System.out.println(" PLAYER NUMBER 1 WITH BLUE MARBLE IS THE WINNER .");
                        else
                            System.out.println(" PLAYER NUMBER 2 WITH RED MARBLE IS THE WINNER .");
                        flag = false;
                    }
                    turn++;
                }
                break;
            case 2:
                System.out.println("PLEAS ENTER TYPE OF BOT PLAY : ");
                System.out.println("1 ) OFFENSIVE .");//priority is to make trail and win game
                System.out.println("2 ) DEFENSIVE .");//priority is to stop opponent to make trail
                int typeOfPlay = myscanner.nextInt();
                while (flag) {
                    if (game.getMap().length == 36)
                        System.out.println("GAME IS OVER !!             DRAW ! ");
                    int numberOfPart = 0, direction = 0;
                    if (2 - turn % 2 == 1) {
                        System.out.println("PLEAS ENTER X AND Y : ");
                        int x = myscanner.nextInt();
                        int y = myscanner.nextInt();
                        game.addMarble(1, x, y);
                        game.printMap();
                        System.out.println("PLEAS ENTER NUMBER OF PART TO TURN :");
                        numberOfPart = myscanner.nextInt();
                        System.out.println("PLEAS ENTER DIRECTION : ");
                        System.out.println("1 ) ROUND CLOCK . ");
                        System.out.println("2 ) COUNTER CLOCKWISE . ");
                        direction = myscanner.nextInt();
                        game.rotatePart(numberOfPart, direction);
                    } else {
                        int[] location = bot.findBestSpot(2);
                        game.addMarble(2, location[0], location[1]);
                        int[] rotationDetail = bot.findBestRotation(2, typeOfPlay);
                        numberOfPart = rotationDetail[0];
                        direction = rotationDetail[1];
                        game.rotatePart(numberOfPart, direction);
                    }
                    System.out.println();
                    game.printMap();
                    int winner = game.checkWin();
                    if (winner > 10) {
                        System.out.println("DRAW ! ");
                        flag = false;
                    }
                    if (winner > 0 && winner < 10) {
                        if ((winner / 2) == 1)
                            System.out.println(" PLAYER NUMBER 1 WITH BLUE MARBLE IS THE WINNER .");
                        else
                            System.out.println(" PLAYER NUMBER 2 WITH RED MARBLE IS THE WINNER .");
                        flag = false;
                    }
                    turn++;
                }
                break;
        }
    }
}
