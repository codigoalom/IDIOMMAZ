package com.alom.idiommaz.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alom.idiommaz.R;

public class AdminActivity extends AppCompatActivity {

    private TextView adminusuariotxtView, adminniveltxtView, adminlecciontxtView,
            irainiciotxtView, adminexpresiontxtView, adminimagentxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        configView();
    }

    private void configView(){
        irainiciotxtView = findViewById(R.id.inicioTextView);
        irainiciotxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, MainActivity.class));

            }
        });

        adminusuariotxtView = findViewById(R.id.adminusuariosTextView);
        adminusuariotxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, UsuarioAdminActivity.class));
            }
        });

        adminniveltxtView = findViewById(R.id.adminnivelesTextView);
        adminniveltxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, NivelAdminActivity.class));
            }
        });

        adminlecciontxtView = findViewById(R.id.adminleccionesTextView);
        adminlecciontxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LeccionAdminActivity.class));
            }
        });
    }
}
