package com.company;

import com.company.Color;
import com.sun.deploy.security.SelectableSecurityManager;
import sun.font.DelegatingShape;

/**
 * this class is subclass of card class
 */
public class ColouredCard extends Card {
    private String color;

    /**
     *
     * @param value value of card
     * @param color color of the card
     */
    public ColouredCard(int value, String color) {
        super(value);
        this.color = color;
    }

    /**
     *
     * @return color of card
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @return label of card
     */
    public String getLabel() {
        return "    " + value + "    ";
    }

    /**
     * set color of console based on color of card 
     */
    public void showColor() {
        if (color.equals("blue"))
            System.out.print(Color.BLUE_BOLD);
        else if (color.equals("green"))
            System.out.print(Color.GREEN_BOLD);
        else if (color.equals("yellow"))
            System.out.print(Color.YELLOW_BRIGHT);
        else
            System.out.print(Color.RED_BOLD);
    }
}
