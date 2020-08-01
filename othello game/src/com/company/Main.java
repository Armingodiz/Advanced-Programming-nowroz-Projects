package com.company;

import com.company.Bot;
import com.company.PlayGround;
import com.company.Player;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner myscanner = new Scanner(System.in);
        PlayGround test = new PlayGround();
        Player player1 = new Player(1, test);
        Player player2 = new Player(2, test);
        Bot bot = new Bot(2, test);
        player1.addNut(new Nut(1, "4E"));
        player1.addNut(new Nut(1, "5D"));
        player2.addNut(new Nut(2, "5E"));
        player2.addNut(new Nut(2, "4D"));
        System.out.println("                                                                               HELLO !  WELL COME TO OTHELLO GAME !!! ");
        System.out.println();
        test.printMap();
        int flag = 1;
        int turn = 1;
        System.out.println("PLEAS ENTER MOOD OF GAME : ");
        System.out.println("1 ) 1 VS BOT");
        System.out.println("2 ) 1 VS 1");
        int input = myscanner.nextInt();
        switch (input) {
            case 1:
                while (flag == 1) {
                    test.printMap();
                    player1.updatePlayerStatus();
                    bot.updatePlayerStatus();
                    if (!player1.checkPass() && !bot.checkPass()) {
                        System.out.println("GAME IS OVER");
                        flag = 0;
                        int x=player1.getCountOfNuts();
                        int y=bot.getCountOfNuts();
                        if (x>y)
                            System.out.println("YOU WIN ! ");
                        else if (y>x)
                            System.out.println("BOT WIN !");
                        else
                            System.out.println("DRAW !");
                    } else if (turn % 2 == 1) {
                        if (player1.checkPass())
                            player1.makeMove();
                        else {
                            System.out.println("PASSED");
                            bot.makeMove();
                            turn++;
                        }
                    } else {
                        if (bot.checkPass())
                            bot.makeMove();
                        else {
                            System.out.println("PASSED");
                            if (player1.checkPass())
                                player1.makeMove();
                            else {
                                System.out.println("GAME IS OVER !");
                                flag = 0;
                                int x=player1.getCountOfNuts();
                                int y=bot.getCountOfNuts();
                                if (x>y)
                                    System.out.println("YOU WIN ! ");
                                else if (y>x)
                                    System.out.println("BOT WIN !");
                                else
                                    System.out.println("DRAW !");
                            }
                            turn++;
                        }
                    }
                    turn++;
                }
                break;
            case 2:
                while (flag == 1) {
                    player1.updatePlayerStatus();
                    player2.updatePlayerStatus();
                    if (!player1.checkPass() && !player2.checkPass()) {
                        System.out.println("GAME IS OVER");
                        flag = 0;
                        int x=player1.getCountOfNuts();
                        int y=player2.getCountOfNuts();
                        if (x>y)
                            System.out.println("PLAYER NUMBER 1 WON  ! ");
                        else if (y>x)
                            System.out.println("PLAYER NUMBER 2 WON  !");
                        else
                            System.out.println("DRAW !");
                    } else if (turn % 2 == 1) {
                        if (player1.checkPass())
                            player1.makeMove();
                        else {
                            System.out.println("PASSED");
                            player2.makeMove();
                            turn++;
                        }
                    } else {
                        if (player2.checkPass())
                            player2.makeMove();
                        else {
                            System.out.println("PASSED");
                            player1.makeMove();
                            turn++;
                        }
                    }
                    test.printMap();
                    turn++;
                }
                break;
        }
    }
}
