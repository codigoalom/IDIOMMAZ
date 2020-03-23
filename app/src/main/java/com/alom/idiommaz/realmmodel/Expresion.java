package com.alom.idiommaz.realmmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Expresion extends RealmObject {

    @PrimaryKey
    private int id;
    private String Expresion;
    private String ImagenFk;
    private String NivelExpresionFk;
    private String LeccionExpresionFk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivelExpresionFk() {
        return NivelExpresionFk;
    }

    public String getLeccionExpresionFk() {
        return LeccionExpresionFk;
    }

    public String getOracionExpresionFk() {
        return OracionExpresionFk;
    }

    private String OracionExpresionFk;

    public Expresion(){}

    public String getExpresion(){
        return Expresion;
    }

    public void setExpresion(String expresion){
        Expresion = expresion;
    }

    public void setImagenFk(String imagenFk){
        ImagenFk = imagenFk;
    }

    public String getImagenFk(){
        return ImagenFk;
    }

    public void setNivelExpresionFk(String nivelExpresionFk){
        NivelExpresionFk = nivelExpresionFk;
    }

    public String getNivelEnExpresionFk(){
        return NivelExpresionFk;
    }

    public void setLeccionExpresionFk(String leccionExpresionFk){
        LeccionExpresionFk = leccionExpresionFk;
    }

    public String getLeccionEnExpresionFk(){
        return LeccionExpresionFk;
    }

    public void setOracionExpresionFk(String oracionExpresionFk){
        OracionExpresionFk = oracionExpresionFk;
    }

    public String getOracionEnExpresionFk(){
        return OracionExpresionFk;
    }
}
