package com.faberospina.cine;

/**
 * Created by Edwin on 19/10/2016.
 */

public class Contacto {

    private String nombre, pass, mail, id,fecha;
    int year;

    public Contacto() {
    }

    public Contacto(String nombre, String pass, String mail, String id,int year) {
        this.nombre = nombre;
        this.pass = pass;
        this.mail = mail;
        this.id = id;
        this.year=year;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public String getMail() {
        return mail;
    }

    public int getYear(){ return  year;}

    public String getId() {
        return id;
    }
}
