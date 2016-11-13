package com.faberospina.cine;

/**
 * Created by Faber on 27/09/2016.
 */

public class Lista_entrada {
    private int idImagen;
    private String nombre;
    private int precio;
    private  String info;
    private  String info2;
    private volatile transient Object[] array;

    public Lista_entrada(int idImagen, String nombre, int precio, String info, String info2){
        this.idImagen=idImagen;
        this.nombre=nombre;
        this.precio=precio;
        this.info=info;
        this.info2=info2;
    }
    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }
}
