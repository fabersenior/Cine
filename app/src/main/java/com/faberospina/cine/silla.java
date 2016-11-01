package com.faberospina.cine;

/**
 * Created by Faber on 31/10/2016.
 */

public class Silla {

    String letter;
    int pos;


    public Silla(String letter, int pos) {
        this.letter = letter;
        this.pos = pos;
    }

    public String getLetter() {
        return letter;
    }

    public int getPos() {
        return pos;
    }
}
