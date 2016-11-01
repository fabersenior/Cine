package com.faberospina.cine;

/**
 * Created by Faber on 31/10/2016.
 */

public class Catalogo {
    private int idImage, precio;
    private String nombre,descripcion1,descripcion2;

    public Catalogo(int idImage, int precio, String nombre, String descripcion1, String descripcion2) {
        this.idImage = idImage;
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion1 = descripcion1;
        this.descripcion2 = descripcion2;
    }

    public Catalogo(String nombre,int precio, String descripcion1, String descripcion2) {
        this.precio = precio;
        this.nombre = nombre;
        this.descripcion1 = descripcion1;
        this.descripcion2 = descripcion2;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }
}
