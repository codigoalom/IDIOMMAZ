package com.alom.idiommaz.crud;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alom.idiommaz.realmmodel.Leccion;
import com.alom.idiommaz.realmmodel.Nivel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CRUDLeccion {
    private final static int calculateId(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Leccion.class).max("id");
        int NextId;
        if(currentIdNum == null){
            NextId = 0;
        }else {
            NextId = currentIdNum.intValue()+1;
        }
        return NextId;
    }

    public final static void addLeccion(final String nivel, final Leccion leccion){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Nivel nivelRealm = realm.where(Nivel.class).equalTo("nivel", nivel).findFirst();
        nivelRealm.getLecciones().add(leccion);
        realm.insertOrUpdate(nivelRealm);
        realm.commitTransaction();
    }

    public final static List<Leccion> getAllLecciones(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Leccion> lecciones = realm.where(Leccion.class).findAll();
        return lecciones;
    }

    public final static RealmList<Leccion> getLeccionesByLevel(String nivel){
        Realm realm = Realm.getDefaultInstance();
        RealmList<Leccion> lecciones = realm.where(Nivel.class).equalTo("nivel", nivel).findFirst().getLecciones();
        return lecciones;
    }

    public final static Leccion getLeccionByName(String lecc){
        Realm realm = Realm.getDefaultInstance();
        Leccion leccion = realm.where(Leccion.class).equalTo("leccion", lecc).findFirst();
        return leccion;
    }

    public final static Leccion getLeccionById(int id){
        Realm realm = Realm.getDefaultInstance();
        Leccion leccion = realm.where(Leccion.class).equalTo("id", id).findFirst();
        return leccion;
    }

    public final static void updateLeccionById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Leccion leccion = realm.where(Leccion.class).equalTo("id", id).findFirst();
        leccion.setLeccion("At a restaurant");
        realm.insertOrUpdate(leccion);
        realm.commitTransaction();
    }

    public final static void deleteLeccionById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Leccion leccion = realm.where(Leccion.class).equalTo("id", id).findFirst();
        leccion.deleteFromRealm();
        realm.commitTransaction();
    }

    public final static void deleteAllLecciones(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Leccion> lecciones = realm.where(Leccion.class).findAll();
        lecciones.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
