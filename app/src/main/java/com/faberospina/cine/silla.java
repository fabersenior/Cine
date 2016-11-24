package com.faberospina.cine;

/**
 * Created by Faber on 31/10/2016.
 */

public class Silla {

    String letter;
    int idImagen;
    int pos;
    long hora;
    String user;
    int k1,k2,k3,k4;


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

    public Silla(String letter, int pos, int idImagen,long hora,String user,int k1,int k2,int k3, int k4) {
        this.letter = letter;
        this.pos = pos;
        this.idImagen = idImagen;
        this.hora=hora;
        this.user=user;
        this.k1=k1;
        this.k2=k2;
        this.k3=k3;
        this.k4=k4;
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

    public String getUser() {
        return user;
    }

    public int getK1() {
        return k1;
    }

    public int getK2() {
        return k2;
    }

    public int getK3() {
        return k3;
    }

    public int getK4() {
        return k4;
    }

}
