package com.alom.idiommaz.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alom.idiommaz.R;
import com.alom.idiommaz.realmmodel.Usuario;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView speechToTxtTxtView, showtexttolearnTxtView, iraadminTxtView, lessonprogressTxtView;
    private TextToSpeech textToSpeech;
    private SeekBar speedSeekBr, pitchSeekBr;
    private Button comenzarBtn, volveraescucharBtn, continuarBtn, tuturnoBtn, terminarBtn, traducirBtn,
            regresarBtn, mostrartextoBtn;
    private ProgressBar lessonPgrsBr;
    private double lessonprogress;
    private ImageView centerimageImgView;
    private MediaPlayer magicwandSound;
    private MediaPlayer toiletflushSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configUi();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("Main", "Language not supported");
                    }else{
                        //Habilitar botón
                        comenzarBtn.setEnabled(true);
                    }
                }else{
                    Log.e("Main", "Initialization failed");
                }
            }
        });
    }

    private void configUi(){
        speechToTxtTxtView = findViewById(R.id.speechtotextTextView);
        showtexttolearnTxtView = findViewById(R.id.showtexttolearnTextView);
        comenzarBtn = findViewById(R.id.comenzarButton);
        volveraescucharBtn = findViewById(R.id.volveraescucharButton);
        continuarBtn = findViewById(R.id.continuarButton);
        tuturnoBtn = findViewById(R.id.miturnoButton);
        terminarBtn = findViewById(R.id.terminarButton);
        traducirBtn = findViewById(R.id.traducirButton);
        regresarBtn = findViewById(R.id.regresarButton);
        mostrartextoBtn = findViewById(R.id.mostrartextoButton);
        centerimageImgView = findViewById(R.id.centerimageImageView);
        iraadminTxtView = findViewById(R.id.iradminTextView);

        volveraescucharBtn.setEnabled(false);
        continuarBtn.setEnabled(false);
        tuturnoBtn.setEnabled(false);
        terminarBtn.setEnabled(false);
        traducirBtn.setEnabled(false);
        regresarBtn.setEnabled(false);
        mostrartextoBtn.setEnabled(false);
        iraadminTxtView.setVisibility(View.INVISIBLE);
        iraadminTxtView.setEnabled(false);

        speedSeekBr = findViewById(R.id.speedSeekbr);
        pitchSeekBr = findViewById(R.id.pitchSeekbr);
        lessonPgrsBr = findViewById(R.id.lessonprogressBar);
        lessonprogressTxtView = findViewById(R.id.progressbarTxtview);

        magicwandSound = MediaPlayer.create(MainActivity.this,R.raw.magic_wand);
        toiletflushSound = MediaPlayer.create(MainActivity.this,R.raw.toilet_flush);

        comenzarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrar ventana Dialog para entrar nombre de usuario
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.nombre_dialog, null);
                final EditText nombreEdtTxt = mView.findViewById(R.id.entrarnombreEditTtext);
                Button continuarDlgBtn = mView.findViewById(R.id.continuardialogButton);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                //Implementación del botón continuar en la ventana Dialog
                continuarDlgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nombreEdtTxt.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Escriba un nombre para continuar",
                                    Toast.LENGTH_LONG).show();
                        }else{
                            String usuariodeDlg = nombreEdtTxt.getText().toString().trim();
                            dialog.dismiss();
                            Usuario usuario = new Usuario();
                            usuario.setNombre(usuariodeDlg);
                            //Si el usuario es administrador, habilitar opción para ir a Actividad
                            // de Administración: Admin
                            if(usuario.getNombre().equalsIgnoreCase("alejandro")){
                                iraadminTxtView.setVisibility(View.VISIBLE);
                                iraadminTxtView.setEnabled(true);
                                String iraadminstr = getString(R.string.admin_label);
                                iraadminTxtView.setText(iraadminstr);
                            }else{
                                iraadminTxtView.setVisibility(View.INVISIBLE);
                                iraadminTxtView.setEnabled(false);
                                iraadminTxtView.setText("");
                            }

                            terminarBtn.setEnabled(true);
                        }

                        volveraescucharBtn.setEnabled(true);
                        continuarBtn.setEnabled(true);
                        tuturnoBtn.setEnabled(true);
                        mostrartextoBtn.setEnabled(true);
                        showtexttolearnTxtView.setText("");
                        speak("comenzar");
                    }
                });
            }
        });

        iraadminTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            }
        });

        volveraescucharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak("volverescuchar");
            }
        });

        mostrartextoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Ver el texto escrito en inglés puede afectar tu pronunciación",
                        Toast.LENGTH_LONG).show();
            }
        });

        //Continuar lección con el próximo contenido
        continuarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak("continuar");
            }
        });

        regresarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                speak("regresar");
            }
        });

        terminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar el último contenido visto
            }
        });
    }

    private void speak(String boton){
        String text;
        if(boton.matches("comenzar")){
            text = "Volver a implementar";
        }else text = "Volver a implementar";

        float pitch = (float) pitchSeekBr.getProgress() / 50;
        if(pitch < 0.1) pitch = 0.1f;
        float speed = (float) speedSeekBr.getProgress() / 50;
        if(speed < 0.1) speed = 0.1f;

        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Doesn't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int[] imageArrayGood = {
                R.drawable.actor_awsome,
                R.drawable.drhouse_amazing,
                R.drawable.guy_cap_excellent,
                R.drawable.kid_excellent,
                R.drawable.racoon_excellent,
                R.drawable.guy_cap_excellent,
        };

        int[] imageArrayBad = {
                R.drawable.actor_cry,
                R.drawable.obama_notgood,
                R.drawable.obama_notgood0,
                R.drawable.youngactor_cry,
        };

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Todo: obtener textoaprender.trim desde el inicio (desde la base de datos)

                    String textoaprendertrim = "Volver a implementar";

                    if(textoaprendertrim!=null){
                        String textoaprenderlimpio="";
                        if(textoaprendertrim.endsWith("?") || textoaprendertrim.endsWith(".") ||
                                textoaprendertrim.endsWith("!")){
                            textoaprenderlimpio = textoaprendertrim.substring(0, textoaprendertrim.length()-1);
                        }else textoaprenderlimpio = textoaprendertrim;

                        StringBuffer buffer = new StringBuffer();
                        int i;
                        String resultgetandtrim = "";
                        String speechtotextlimpio = "";
                        Boolean coincidencia = false;
                        for(i=0; i<result.size(); i++) {
                            buffer.append(result.get(i) + " ");
                            resultgetandtrim = result.get(i).trim();

                            if(resultgetandtrim.endsWith("?") || resultgetandtrim.endsWith(".")
                                    || resultgetandtrim.endsWith("!")){
                                speechtotextlimpio = resultgetandtrim.substring(0, resultgetandtrim.length()-1);
                            }else speechtotextlimpio = resultgetandtrim;

                            if(speechtotextlimpio.equalsIgnoreCase(textoaprenderlimpio)){
                                magicwandSound.start();
                                coincidencia = true;
                                speechToTxtTxtView.setText("You said: " + resultgetandtrim);
                                int randomgood = new Random().nextInt(6);
                                centerimageImgView.setImageResource(imageArrayGood[randomgood]);
                                break;
                            }
                            coincidencia = false;
                        }

                        if(coincidencia==false){
                            toiletflushSound.start();
                            speechToTxtTxtView.setText(result.get(0)); //Se devuelve 0 porque se
                                                                    // supone que es el que más se acerca...
                            int randomgbad = new Random().nextInt(4);
                            centerimageImgView.setImageResource(imageArrayBad[randomgbad]);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}

