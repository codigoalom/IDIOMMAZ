package com.alom.idiommaz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alom.idiommaz.R;
import com.alom.idiommaz.crud.CRUDUsuario;
import com.alom.idiommaz.realmmodel.Usuario;

import io.realm.Realm;

public class UsuarioAdminActivity extends AppCompatActivity {

    private EditText entranomusuarioEdtTxt;
    private Button guardarBtn, mostrartodosusuariosBtn, mostrarusrxnombreBtn, mostrarusrxidBtn,
    actualizarusrxidBtn, borrarusrxidBtn, borrarusrtodosBtn;
    private Usuario usuario;
    private Realm realm;
    private TextView iradmintxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_admin);

        realm = Realm.getDefaultInstance();

        configUi();
    }

    private void configUi(){
        usuario = new Usuario();
        entranomusuarioEdtTxt = findViewById(R.id.entrarnomadminEditText);
        guardarBtn = findViewById(R.id.guardarusuarioButton);
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(entranomusuarioEdtTxt.getText().toString());
                CRUDUsuario.addUsuario(usuario);
            }
        });

        mostrartodosusuariosBtn = findViewById(R.id.mostrartodosusuariosButton);
        mostrartodosusuariosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.getAllUsuarios();
            }
        });

        mostrarusrxnombreBtn = findViewById(R.id.mostrarusrxnombreButton);
        mostrarusrxnombreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.getUsuarioByName(entranomusuarioEdtTxt.getText().toString());
            }
        });

        mostrarusrxidBtn = findViewById(R.id.mostrarusrxidButton);
        mostrarusrxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.getUsuarioById(Integer.parseInt(entranomusuarioEdtTxt.getText().toString()));
            }
        });

        actualizarusrxidBtn = findViewById(R.id.actualizarxidButton);
        actualizarusrxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.updateUsuarioById(Integer.parseInt(entranomusuarioEdtTxt.getText().toString()));
            }
        });

        borrarusrxidBtn = findViewById(R.id.borrarusrxidButton);
        borrarusrxidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.deleteUsuarioById(Integer.parseInt(entranomusuarioEdtTxt.getText().toString()));
            }
        });

        borrarusrtodosBtn = findViewById(R.id.borrausrtodosButton);
        borrarusrtodosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDUsuario.deleteAllUsuarios();
            }
        });

        iradmintxtView = findViewById(R.id.iraadminTextView);
        iradmintxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsuarioAdminActivity.this, AdminActivity.class));
            }
        });
    }
}
