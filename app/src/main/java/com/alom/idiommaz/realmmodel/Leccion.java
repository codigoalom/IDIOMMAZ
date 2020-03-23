package com.alom.idiommaz.realmmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Leccion extends RealmObject {

    private String leccion;

    public Leccion() {
    }

    public String getLeccion() {
        return leccion;
    }

    public void setLeccion(String leccion) {
        this.leccion = leccion;
    }
}
