package com.alom.idiommaz.realmmodel;

import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Nivel extends RealmObject {

    @PrimaryKey
    private int id;
    private String nivel;
    private RealmList<Leccion> lecciones;

    public Nivel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public RealmList<Leccion> getLecciones() {
        return lecciones;
    }

    public void setLecciones(RealmList<Leccion> lecciones) {
        this.lecciones = lecciones;
    }
}
