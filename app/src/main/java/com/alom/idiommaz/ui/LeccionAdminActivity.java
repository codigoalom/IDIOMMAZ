package com.alom.idiommaz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alom.idiommaz.R;
import com.alom.idiommaz.crud.CRUDLeccion;
import com.alom.idiommaz.realmmodel.Leccion;

import io.realm.Realm;

public class LeccionAdminActivity extends AppCompatActivity {

    private Leccion leccion;
    private EditText entrarleccionedtTxt, entrarnivedtTxt;
    private Button guardarleccBtn, mostrartodasleccsBtn, mostrarleccxnombreBtn, mostrarleccxidBtn,
            actualizarleccxidBtn, borrarleccxidBtn, borrarlecctodasBtn;
    private Realm realm;
    private TextView iradmintxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leccion_admin);

        realm = Realm.getDefaultInstance();

        configLeccAdmUi();
    }

    private void configLeccAdmUi(){
        leccion = new Leccion();
        entrarleccionedtTxt = findViewById(R.id.entrarleccionEditText);
        entrarnivedtTxt = findViewById(R.id.entrarnivelEditText);
        guardarleccBtn = findViewById(R.id.guardarleccionButton);
        guardarleccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leccion.setLeccion(entrarleccionedtTxt.getText().toString());
                String nivel = entrarnivedtTxt.getText().toString();
                Log.d("Main", "Lección en botón guardar: " + leccion.getLeccion() +
                        " Id de Nivel: " + nivel);
                CRUDLeccion.addLeccion(nivel, leccion);
            }
        });

        mostrartodasleccsBtn = findViewById(R.id.mostrartodosleccionesButton);
        mostrartodasleccsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.getAllLecciones();
            }
        });

        mostrarleccxnombreBtn = findViewById(R.id.mostrarleccionxnombreButton);
        mostrarleccxnombreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.getLeccionByName(entrarleccionedtTxt.getText().toString());
            }
        });

        mostrarleccxidBtn = findViewById(R.id.mostrarleccionxidButton);
        mostrarleccxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.getLeccionById(Integer.parseInt(entrarleccionedtTxt.getText().toString()));
            }
        });

        actualizarleccxidBtn = findViewById(R.id.actualizarleccionxidButton);
        actualizarleccxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.updateLeccionById(Integer.parseInt(entrarleccionedtTxt.getText().toString()));
            }
        });

        borrarleccxidBtn = findViewById(R.id.borrarleccionxidButton);
        borrarleccxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.deleteLeccionById(Integer.parseInt(entrarleccionedtTxt.getText().toString()));
            }
        });

        borrarlecctodasBtn = findViewById(R.id.borraleccionestodosButton);
        borrarlecctodasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDLeccion.deleteAllLecciones();
            }
        });

        iradmintxtView = findViewById(R.id.iraadminTextView);
        iradmintxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeccionAdminActivity.this, AdminActivity.class));
            }
        });
    }
}
