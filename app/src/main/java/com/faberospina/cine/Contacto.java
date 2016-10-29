package com.faberospina.cine;

/**
 * Created by Edwin on 19/10/2016.
 */

public class Contacto {

    private String nombre, telefono, mail, id;

    public Contacto() {
    }

    public Contacto(String nombre, String telefono, String mail, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.mail = mail;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getId() {
        return id;
    }
}
