package com.company;

import com.company.PlayGround;
import com.company.PlayWithBot;
import com.sun.corba.se.impl.legacy.connection.USLPort;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Menue game = new Menue();
        game.start();
        int flag = 1;
        while (flag == 1) {
            Scanner scanner = new Scanner(System.in);
            for (int i=0;i<10;i++){
            System.out.println();}

            System.out.println("                                                                 1 ) NEW GAME .");
            System.out.println();
            System.out.println("                                                                 2 ) EXIT .");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    Menue game1 = new Menue();
                    game1.start();
                    break;
                case 2:
                    flag = 0;
                    break;
            }
        }
    }
}
