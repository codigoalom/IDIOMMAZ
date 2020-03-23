package com.alom.idiommaz.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alom.idiommaz.R;
import com.alom.idiommaz.crud.CRUDLeccion;
import com.alom.idiommaz.crud.CRUDNivel;
import com.alom.idiommaz.realmmodel.Leccion;
import com.alom.idiommaz.realmmodel.Nivel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class NivelAdminActivity extends AppCompatActivity {

    private Nivel nivel;
    private Button guardarnivBtn, borrarnivtodosBtn, borrarnivxnombreBtn;
    private Realm realm;
    private TextView iradmintxtView;
    private List<String> cadanivel;
    private Map<Integer, String> levelsmap;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel_admin);

        realm = Realm.getDefaultInstance();
        //Este objeto necesita inicializar con una lista concreta, de lo contrario inicializa
        //con una lista abstracta y luego no permite la función adapter.clear() para actualizar spinner
        cadanivel = new ArrayList<>(Arrays.asList("Make a Selection",
                "Level A1 not in DB", "Level A2 not in DB",
                "Level B1 not in DB", "Level B2 not in DB", "Level C1 not in DB",
                "Level C2 not in DB"));

        levelsmap = new HashMap<>();

        //Map que sirve como plantilla para poblar el spinner en orden
        levelsmap.put(1, "A1");
        levelsmap.put(2, "A2");
        levelsmap.put(3, "B1");
        levelsmap.put(4, "B2");
        levelsmap.put(5, "C1");
        levelsmap.put(6, "C2");

        configNivAdmUi();
    }

    private void poblarSpinner(){
        //Esta función llena el Spinner en caso de que haya niveles existentes o no en BD
        //La función está cargando la info de Base de Datos cuando OnCreate de la Actividad,
        //pero no está refrescando cuando los usuarios borran o añaden un nivel.

        List<String> nivelesenbd = new ArrayList<>();
        List<String> updatecadanivel = new ArrayList<>(cadanivel);
        if(CRUDNivel.getAllNiveles().size()!=0){

            //Buscar niveles existentes en BD
            for(int i=0; i<CRUDNivel.getAllNiveles().size(); i++){
                nivelesenbd.add(CRUDNivel.getAllNiveles().get(i).getNivel());
            }
            for(Map.Entry<Integer, String> entry : levelsmap.entrySet()){
                //Busca la posición de los niveles en el Map nivelesmap
                if(nivelesenbd.contains(entry.getValue())){
                    updatecadanivel.set(entry.getKey(), "Level " + entry.getValue() + " is in DB");
                }else updatecadanivel.set(entry.getKey(), "Level " + entry.getValue() + " not in DB");
            }
        }else {
            updatecadanivel = new ArrayList<>(Arrays.asList("Make a Selection",
                    "Level A1 not in DB", "Level A2 not in DB",
                    "Level B1 not in DB", "Level B2 not in DB", "Level C1 not in DB",
                    "Level C2 not in DB"));
        }
        //Actualizar adapter en Spinner con nuevos cambios en cada nivel
        //Limpiar lista cadanivel para actualizar info en Spinner
        //Esta función es obligatoria antes de llamar adapter.notifyDataSetChanged();
        cadanivel.clear();
        cadanivel.addAll(updatecadanivel);
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    private void configNivAdmUi(){
        nivel = new Nivel();
        poblarSpinner();
        guardarnivBtn = findViewById(R.id.guardarnivelButton);
        guardarnivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nivel.getNivel()==null){
                    Toast.makeText(NivelAdminActivity.this,
                            "Escoja un nivel para administrar", Toast.LENGTH_LONG).show();
                }else if(nivel.getNivel()!=null){
                    Boolean nivelexiste=false;
                    for(Nivel mNivel: CRUDNivel.getAllNiveles()){
                        if(mNivel.getNivel()!=null && mNivel.getNivel().matches(nivel.getNivel())){
                            nivelexiste=true;
                            Toast.makeText(NivelAdminActivity.this,
                                    "El nivel " + nivel.getNivel() + " ya existe en la BD",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    if(nivelexiste==false && nivel.getNivel()!=null){
                        CRUDNivel.addNivel(nivel);
                        poblarSpinner();
                        Toast.makeText(NivelAdminActivity.this,
                                "Se guardó el nivel " + nivel.getNivel() + " en la BD",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        borrarnivtodosBtn = findViewById(R.id.borranivelestodosButton);
        borrarnivtodosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!CRUDNivel.getAllNiveles().isEmpty()){
                    //Mostrar ventana Dialog para confirmar borrar todos niveles
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(NivelAdminActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.nivel_dialog, null);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    final Button borrardialogBtn, cancelardialogBtn;
                    borrardialogBtn = mView.findViewById(R.id.borrardialogButton);
                    borrardialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CRUDNivel.deleteAllNiveles();
                            poblarSpinner();
                            Toast.makeText(NivelAdminActivity.this, "Se borraron todos los niveles",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                    cancelardialogBtn = mView.findViewById(R.id.cancelardialogButton);
                    cancelardialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }else Toast.makeText(NivelAdminActivity.this, "No hay niveles en la BD",
                        Toast.LENGTH_LONG).show();

            }
        });

        iradmintxtView = findViewById(R.id.iraadminTextView);
        iradmintxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NivelAdminActivity.this, AdminActivity.class));
            }
        });

        borrarnivxnombreBtn = findViewById(R.id.borranivelxnombreButton);
        borrarnivxnombreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nivel.getNivel()!=null && CRUDNivel.getNivelByName(nivel.getNivel())!=null){
                    CRUDNivel.deleteNivelByName(nivel.getNivel());
                    poblarSpinner();
                    Toast.makeText(NivelAdminActivity.this,
                            "Se eliminó el nivel " + nivel.getNivel() + " de la BD", Toast.LENGTH_LONG).show();
                }else if(nivel.getNivel()==null){
                    Toast.makeText(NivelAdminActivity.this, "Selecciona un nivel para borrar",
                            Toast.LENGTH_LONG).show();
                }else Toast.makeText(NivelAdminActivity.this, "El nivel " + nivel.getNivel() +
                        " no existe en la BD", Toast.LENGTH_LONG).show();
            }
        });

        //Spinner para mostrar Niveles
        final Spinner managelevelsSpnr = findViewById(R.id.managelevelSpinner);
        adapter = new ArrayAdapter<>(this,
                R.layout.custom_spinner, R.id.text1, cadanivel);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        managelevelsSpnr.setAdapter(adapter);
        managelevelsSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (String.valueOf(id)){
                    case "1":
                        nivel.setNivel("A1");
                        mostrarLeccionesXNivel();
                        break;
                    case "2":
                        nivel.setNivel("A2");
                        mostrarLeccionesXNivel();
                        break;
                    case "3":
                        nivel.setNivel("B1");
                        mostrarLeccionesXNivel();
                        break;
                    case "4":
                        nivel.setNivel("B2");
                        mostrarLeccionesXNivel();
                        break;
                    case "5":
                        nivel.setNivel("C1");
                        mostrarLeccionesXNivel();
                        break;
                    case "6":
                        nivel.setNivel("C2");
                        mostrarLeccionesXNivel();
                        break;
                    default:
                        nivel.setNivel(null);
                        mostrarLeccionesXNivel();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void mostrarLeccionesXNivel(){
        StringBuffer bufferlecclist = new StringBuffer();
        TextView leccionesdeniveltxtView = findViewById(R.id.leccionesdenivTextView);
        if(nivel.getNivel()!=null && CRUDNivel.getNivelByName(nivel.getNivel())!=null){
            for (Leccion mLeccion: CRUDNivel.getNivelByName(nivel.getNivel()).getLecciones()){
                bufferlecclist.append(mLeccion.getLeccion() + "\n");
            }
            if(bufferlecclist.length()!=0){
                leccionesdeniveltxtView.setText(bufferlecclist.toString());
            }else leccionesdeniveltxtView.setText("El nivel " + nivel.getNivel() +
                    " no tiene lecciones en BD");
        }else leccionesdeniveltxtView.setText("");
    }
}
