package com.company;

/**
 * this class is a subclass of Card class and show
 * type of Black card
 */
public class WildCard extends Card {
    private int type;//type 1 is for black card and 2 is for wild card

    /**
     * @param value is value for card
     * @param type  is type of black card
     */
    public WildCard(int value, int type) {
        super(value);
        this.type = type;
    }

    /**
     * @return label of card
     */
    public String getLabel() {
        if (type == 1)
            return "    50   ";
        else
            return "WILD DRAW";
    }
}
