package com.alom.idiommaz.realmmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Imagen extends RealmObject {

    @PrimaryKey
    private int id;
    private String Nombre;

    public Imagen(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private byte[] Imagen;

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nomImagen) {
        Nombre = nomImagen;
    }
}
