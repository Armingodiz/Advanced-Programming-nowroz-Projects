package com.company;

public class Card {
    private String label;//the string which will be printed in the middle of card
    int value;//value of the card

    /**
     * @param value of card
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * @return value of card
     */
    public int getValue() {
        return value;
    }

    /**
     * @return label of card
     */
    public String getLabel() {
        return label;
    }
}
