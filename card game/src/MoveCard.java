package com.company;

/**
 * this class is a subclass of ColouredCard and has type
 * variable for kind of move move card can cause
 */
public class MoveCard extends ColouredCard {
    private String type;

    /**
     *
     * @param value is value of card
     * @param color is color of card
     * @param type is type of move card
     */
    public MoveCard( int value,String color, String type) {
        super( value,color);
        this.type = type;
    }
    /**
     * @return label of card
     */
    public String getLabel() {
        if (type.equals("Skip"))
            return "  Skip   ";
        else if (type.equals("Reverse"))
            return " Reverse ";
        else
            return "  Draw2  ";
    }
}
