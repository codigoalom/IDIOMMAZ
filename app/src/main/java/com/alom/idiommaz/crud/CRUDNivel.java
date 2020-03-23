package com.alom.idiommaz.crud;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.alom.idiommaz.R;
import com.alom.idiommaz.realmmodel.Nivel;
import com.alom.idiommaz.realmmodel.Usuario;
import com.alom.idiommaz.ui.NivelAdminActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CRUDNivel {
    private final static int calculateId(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Nivel.class).max("id");
        int NextId;
        if(currentIdNum == null){
            NextId = 0;
        }else {
            NextId = currentIdNum.intValue()+1;
        }
        return NextId;
    }

    public final static void addNivel(final Nivel nivel){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = CRUDNivel.calculateId();
                Nivel realmNivel = realm.createObject(Nivel.class, index);
                realmNivel.setNivel(nivel.getNivel());
            }
        });
    }

    public final static List<Nivel> getAllNiveles(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Nivel> niveles = realm.where(Nivel.class).findAll();
        return niveles;
    }

    public final static Nivel getNivelByName(String niv){
        Realm realm = Realm.getDefaultInstance();
        Nivel nivel = realm.where(Nivel.class).equalTo("nivel", niv).findFirst();
        return nivel;
    }

    public final static Nivel getNivelById(int id){
        Realm realm = Realm.getDefaultInstance();
        Nivel nivel = realm.where(Nivel.class).equalTo("id", id).findFirst();
        if(nivel!=null){
            Log.d("Main", "id " + nivel.getId() + " Nombre del nivel " + nivel.getNivel());
        }
        return nivel;
    }

    public final static void updateNivelById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Nivel nivel = realm.where(Nivel.class).equalTo("id", id).findFirst();
        nivel.setNivel("A1");
        realm.insertOrUpdate(nivel);
        realm.commitTransaction();
        if(nivel!=null){
            Log.d("Main", "id " + nivel.getId() + " Nombre del nivel " + nivel.getNivel());
        }
    }

    public final static void deleteNivelByName(String level){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Nivel nivel = realm.where(Nivel.class).equalTo("nivel", level).findFirst();
        if(nivel!=null){
            nivel.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public final static void deleteNivelById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Nivel nivel = realm.where(Nivel.class).equalTo("id", id).findFirst();
        nivel.deleteFromRealm();
        realm.commitTransaction();
    }

    public final static Boolean deleteAllNiveles(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Nivel> niveles = realm.where(Nivel.class).findAll();
        Boolean success = niveles.deleteAllFromRealm();
        realm.commitTransaction();
        return success;
    }
}
