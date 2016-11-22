package com.faberospina.cine;

/**
 * Created by Faber on 31/10/2016.
 */

public class Silla {

    String letter;
    int idImagen;
    int pos;
    long hora;



    public Silla(String letter, int pos, int idImagen) {
        this.letter = letter;
        this.pos = pos;
        this.idImagen = idImagen;
    }

    public Silla(String letter, int pos, int idImagen,long hora) {
        this.letter = letter;
        this.pos = pos;
        this.idImagen = idImagen;
        this.hora=hora;
    }

    public String getLetter() {
        return letter;
    }

    public int getPos() {
        return pos;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public long getHora() {
        return hora;
    }
}
