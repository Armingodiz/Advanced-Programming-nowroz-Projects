package com.company;
/**
 * this class is a subclass of ColouredCard and
 * just has a value for card
 */
public class NormalCard extends ColouredCard {
    /**
     *
     * @param value of card
     * @param color of card
     */
    public NormalCard(int value, String color) {
        super(value, color);
    }

    /**
     *
     * @return label of card
     */
    public String getLabel() {
        if (value > 9)
            return "   " + value + "    ";
        else
            return "    " + value + "    ";
    }
}
