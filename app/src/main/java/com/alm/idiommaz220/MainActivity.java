package com.alm.idiommaz220;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper idiommazDb;
    TextView speechToTxtTxtView, showtexttolearnTxtView;
    private TextToSpeech mTTS;
    private SeekBar speedSeekBr, pitchSeekBr;
    Button comenzarBtn, volveraescucharBtn, continuarBtn, tuturnoBtn, terminarBtn, traducirBtn,
            regresarBtn, mostrartextoBtn;
    Nivel nivela1, nivela2, nivelb1, nivelb2, nivelc1, nivelc2;
    Leccion leccion1, leccion2, leccionencurso;
    Contenido contenido1, contenido2, contenido3;
    String contbruto, ultimoindice, textoaprender;
    int cont;
    ProgressBar lessonPgrsBr;
    TextView lessonprogressTxtView;
    private ArrayList<String> lecciones = new ArrayList<>();
    double lessonprogress;
    Usuario usuario;
    ImageView centerimageImgView;
    MediaPlayer magicwandSound;
    MediaPlayer toiletflushSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idiommazDb = new DatabaseHelper(this);
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
        nivela1 = new Nivel();
        nivela2 = new Nivel();
        nivelb1 = new Nivel();
        nivelb2 = new Nivel();
        nivelc1 = new Nivel();
        nivelc2 = new Nivel();
        leccion1 = new Leccion();
        leccion2 = new Leccion();
        leccionencurso = new Leccion();
        contenido1 = new Contenido();
        contenido2 = new Contenido();
        contenido3 = new Contenido();
        volveraescucharBtn.setEnabled(false);
        continuarBtn.setEnabled(false);
        tuturnoBtn.setEnabled(false);
        terminarBtn.setEnabled(false);
        traducirBtn.setEnabled(false);
        regresarBtn.setEnabled(false);
        mostrartextoBtn.setEnabled(false);
        cont = 0;
        speedSeekBr = findViewById(R.id.speedSeekbr);
        pitchSeekBr = findViewById(R.id.pitchSeekbr);
        lessonPgrsBr = findViewById(R.id.lessonprogressBar);
        lessonprogressTxtView = findViewById(R.id.progressbarTxtview);
        textoaprender="";
        usuario = new Usuario();
        magicwandSound = MediaPlayer.create(MainActivity.this,R.raw.magic_wand);
        toiletflushSound = MediaPlayer.create(MainActivity.this,R.raw.toilet_flush);

        nivela1.setNivel("A1");
        nivela2.setNivel("A2");
        nivelb1.setNivel("B1");
        nivelb2.setNivel("B2");
        nivelc1.setNivel("C1");
        nivelc2.setNivel("C2");

        leccion1.setNomLeccion("Personal Information");
        leccion1.setNivelFkEnCont(idiommazDb.getIDFromNivel("A1"));

        leccion2.setNomLeccion("At a Restaurant");
        leccion2.setLeccionFkEnCont(idiommazDb.getIDFromNivel("A1"));

        lecciones.add(leccion1.getNomLeccion());
        lecciones.add(leccion2.getNomLeccion());

        String[] cont1 = {"Hello", "My name is Pedro", "I'm from Venezuela", "Nice to meet you",
                "Nice to meet you too", "Are you Colombian?", "Yes I am", "Are you Cuban?", "No I'm not",
                "Sorry", "Are you from America?", "No I´m not", "Where are you from?",
                "I'm from Russia", "What city are you from?", "I'm from Moscow", "What nationality are you?",
                "I'm Russian. And you?", "I'm Venezuelan", "Is your name Caroline?", "No, it's not",
                "What's your name?", "My name is Riksi", "What's your second name?", "My second name is Eyleen",
                "What's your last name?", "My last name is Ruiz", "What's your phone number?",
                "My phone number is 3 2 0 8 6 2 8 9 6 2", "What's your address?",
                "My address is 69th Avenue number 64 51", "Who is this?", "This is Alejandro",
                "He is my boyfriend", "Nice to meet you", "Nice to meet you too", "Bye bye", "Bye"
        };

        contenido1.setNomLeccion("Personal Information");
        contenido1.setCont(cont1);
        contenido1.setNivelfk("1");
        contenido1.setLeccionfk("1");

        String[] cont2 = {"Restaurant", "waiter", "steak", "chicken", "fish", "Good morning", "May I take your order?",
                "Would Jim like steak?",
                "No, he wouldn't","Good morning", "May I take your order?", "Would Jim like chicken?",
                "No, he wouldn't",
                "What would he like?", "He would like fish", "Good afternoon", "May I take your order?",
                "Would Mary like tea?",
                "No, she wouldn't", "Good afternoon", "May I take your order?", "Would Mary like coffee?",
                "No, she wouldn't",
                "Would she like water or juice?", "Mary would like juice with sugar",
                "Good evening", "Would Caroline like wine with ice? or tea with lemon",
                "She would like wine with ice",
                "Good evening", "Would Abel like a glass of beer? or a bottle of wine",
                "Abel would like a cup of tea", "Would he like a cup of tea or a cup of coffee?",
                "He would like a cup of coffee with sugar", "What would Riksi like for breakfast?",
                "She would like an egg for breakfast",
                "Would she like an orange or an apple for breakfast?",
                "She would like an apple for breakfast", "Would Santia like a banana for breakfast?",
                "No, she wouldn't", "She would like a piece of cake for breakfast",
                "What would Santiago like for dessert?",
                "Santiago would like a piece of pie for dessert",
                "Would Ceida like ice-cream for dessert?", "Yes, she would",
                "What kind of ice-cream would she like for dessert?",
                "She would like vanilla ice-cream for dessert",
                "Would Riksi like chocolate ice-cream? or strawberry ice-cream",
                "She would like strawberry ice-cream for dessert", "Food for breakfast",
                "I like muffin", "I like to eat toast", "I don't like to eat jam", "I don't like butter",
                "Food for lunch", "How many hamburgers would you like to eat?",
                "I'd like to eat two hamburgers", "How many sandwiches would you like to eat?",
                "I'd like to eat three sandwiches",
                "salad", "french fries", "Food for dinner", "rice", "soup", "bread", "cheese",
                "Tom is a waiter", "Mary is a waitress", "Riksi is a customer",
                "How much scrambled egg would Caroline like to eat?",
                "She would like to eat little scrambled egg",
                "How many fried eggs would Fidel like to eat?", "He would like to eat four fried eggs",
                "How many pancakes would you like to eat?", "I'd like to eat five pancakes",
                "What would you like for appetizer?", "I'd like a salad, please",
                "What would you like for side dish?", "I'd like potatoe, please",
                "What would you like for main course?", "I'd like a steak, please",
                "What would you like to drink?", "I'd like to drink soda, please"
        };

        contenido2.setNomLeccion("At the restaurant");
        contenido2.setLeccionfk("2");
        contenido2.setNivelfk("1");
        contenido2.setCont(cont2);

        String[] cont3 = {"Job", "What's your job?", "Where does Caroline work?", "She works at McDonalds",
                "What does she do?", "She is a waitress", "Where does she work?", "She works at Wallmart",
                    "What do you do?", "I'm a teacher", "I work at the National University",
                "I work in a University", "I work in a restaurant", "I work in a school", "I work at an office",
                "Where do you work?", "Do you work at McDonalds?", "Yes, I do", "Do you work at the National University?",
                "No, I don't", "Do you work in a restaurant?", "Yes, I do", "Do you work in a school?",
                "No, I don't", "The meeting is boring", "The meeting is exciting", "Is the meeting boring?",
                "No, it isn't", "Is the meeting exciting?", "Yes it is", "The meeting is interesting",
                "Is the class exciting?", "Yes, it is", "How exciting!", "How interesting!", "This is a computer",
                "This is a picture", "There is a phone on the table", "There is a map on the wall",
                "There's a lamp on the table", "There is a desk in the room", "There's a chair behind the table",
                "Is there a pen on the table?", "Yes, there is", "Is there a pencil on the table?", "Yes, there is",
                "Is there a magazine on the floor?", "No, there isn't", "Is there a newspaper under the table?",
                "No, there isn't", "Is there a book next to the notebook?", "Yes, there is",
                "Is there a piece of paper next to the book?", "No, there isn't", "Is there a clock on the wall?",
                "Yes, there is", "Is there a carpet on the floor?", "Are there drawers in the desk?",
                "Yes, there are", "There are three drawers in the desk", "Where are the three drawers?",
                "I live in Bogota", "Do you live in Carora?", "Yes, I do", "Do you live in Venezuela?",
                "No, I don't", "I live on the 69th avenue", "Riksi lives on the 64th street",
                "Where does Riksi live?", "Does she live on the 63rd street?", "Does Alexis speak Spanish?",
                "Yes, he does", "Does Jimmy speak English?", "No, he doesn't", "What language does he speak?",
                "He speaks Spanish", "Do Riksy and Caroline speak English?", "Yes, they do", "Does Danna read books?",
                "Yes, she does", "Does Riksy read terror books?", "Yes, she does", "How many books does Santiago read?",
                "Santiago reads two books", "Do Jimmy and Jeimy read books?", "No, they don't", "They don't like to read books",
                "Does Jimmy write emails?", "No, he doesn't", "Does Jeimy write emails", "No, she doesn't",
                "Who writes emails?", "Alejandro does", "Does Caroline work?", "No, she doesn't", "Does Alexis work?",
                "No, he doesn't", "Who works?", "Alejandro does", "Who uses the computer?", "Riksy and Alejandro do",
                "Does Norelys write letters?", "No, she doesn't", "Does she write novels?", "No, she doesn't",
                "What does she write?", "She writes poems", "Does Riksy write emails to Alejandro?",
                "Yes, she does", "Does Caroline write text messages to Alejo?", "No, she doesn't"
        };

        contenido3.setNomLeccion("What's your job?");
        contenido3.setLeccionfk("3");
        contenido3.setNivelfk("1");
        contenido3.setCont(cont3);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);

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

        insertNivelInDB();
        insertLeccionInDb();
        insertContenidoInDb();

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
                            usuario.setNomUsuario(usuariodeDlg);
                            Cursor usuarios = idiommazDb.getUsuarios();
                            leccionencurso.setFilasEnCont(idiommazDb.getContenidos());

                            if(leccionencurso.getFilasEnCont()!=null){
                                leccionencurso.getFilasEnCont().moveToFirst();
                            }

                            if(usuarios.getCount()!=0){
                                Boolean insertusrnuevoenBd=true;
                                while(usuarios.moveToNext()){
                                    String usuarioenBd = usuarios.getString(1).trim();
                                    if(usuario.getNomUsuario().equalsIgnoreCase(usuarioenBd)){
                                        insertusrnuevoenBd=false;

                                        String contvistoenBd = usuarios.getString(2);

                                        Log.d("Main", "Contenido visto: " + contvistoenBd);

                                        if(contvistoenBd.length()>3){
                                            String nivel = contvistoenBd.substring(0, contvistoenBd.indexOf("a")).trim();
                                            String leccion = contvistoenBd.substring(contvistoenBd.indexOf("a")+1,
                                                    contvistoenBd.indexOf("b")).trim();
                                            String contenido = contvistoenBd.substring(contvistoenBd.indexOf("b")+1,
                                                    contvistoenBd.indexOf("c")).trim();

                                            leccionencurso.setNivelFkEnCont(nivel);
                                            leccionencurso.setLeccionFkEnCont(leccion);
                                            leccionencurso.setContenidoEnCont(contenido);
                                            Log.d("Main", "Se asignó a clase Lección Nivel " +
                                                    leccionencurso.getNivelFkEnCont() + " Lección " +
                                                    leccionencurso.getLeccionFkEnCont() + " Contenido " +
                                                    leccionencurso.getContenidoEnCont());
                                        }else Log.d("Main", "El valor de contenido visto no tiene la" +
                                                "proporción correcta");

                                        do {
                                            if(leccionencurso.getFilasEnCont().getString(2).matches(leccionencurso.getLeccionFkEnCont()) &&
                                                    leccionencurso.getFilasEnCont().getString(4).matches(leccionencurso.getNivelFkEnCont())){
                                                Log.d("Main", "La lección " + leccionencurso.getFilasEnCont().getString(2) +
                                                        " y el nivel " + leccionencurso.getFilasEnCont().getString(4) +
                                                        " coinciden con el usuario. Contenido bruto en BD: " +
                                                        leccionencurso.getFilasEnCont().getString(1));
                                                leccionencurso.setFilasEnCont(leccionencurso.getFilasEnCont());
                                                contbruto = leccionencurso.getFilasEnCont().getString(1);
                                                ultimoindice = leccionencurso.getFilasEnCont().getString(3);
                                                Log.d("Main", "Último índice: " + ultimoindice);
                                                break;
                                            }
                                        }while(leccionencurso.getFilasEnCont().moveToNext());
                                        break;
                                    }
                                    insertusrnuevoenBd=true;
                                }
                                if(insertusrnuevoenBd){
                                    usuario.setContVisto("1a1b0c");
                                    idiommazDb.insertUsuario(usuario);
                                    Toast.makeText(MainActivity.this, "Se guardó el usuario " +
                                            usuario.getNomUsuario() + " en la Base de Datos", Toast.LENGTH_LONG).show();
                                    leccionencurso.setFilasEnCont(leccionencurso.getFilasEnCont());
                                    contbruto = leccionencurso.getFilasEnCont().getString(1);
                                    ultimoindice = leccionencurso.getFilasEnCont().getString(3);
                                    leccionencurso.setContenidoEnCont("1");
                                }
                            } else {
                                usuario.setContVisto("1a1b0c");
                                idiommazDb.insertUsuario(usuario);
                                Toast.makeText(MainActivity.this, "Se guardó el usuario " +
                                        usuario.getNomUsuario() + " en la Base de Datos", Toast.LENGTH_LONG).show();
                                leccionencurso.setFilasEnCont(leccionencurso.getFilasEnCont());
                                contbruto = leccionencurso.getFilasEnCont().getString(1);
                                ultimoindice = leccionencurso.getFilasEnCont().getString(3);
                                leccionencurso.setContenidoEnCont("1");
                            }
                            terminarBtn.setEnabled(true);
                        }
                        cont = Integer.parseInt(leccionencurso.getContenidoEnCont());
                        textoaprender = contbruto.substring(contbruto.indexOf(Integer.toString(cont)),
                                contbruto.indexOf(Integer.toString(cont+1)));
                        if(cont>9){
                            textoaprender = textoaprender.substring(2);
                        }
                        if(cont>99){
                            textoaprender = textoaprender.substring(3);
                        } else textoaprender = textoaprender.substring(1);

                        Log.d("Main", "cont en comenzar es: " + cont);
                        lessonprogress = (double) cont/(Integer.parseInt(ultimoindice)-1)*100;
                        lessonPgrsBr.setProgress((int) lessonprogress);
                        lessonprogressTxtView.setText("Lesson Progress " + (int) lessonprogress + "%");
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

                if(cont>99){
                    textoaprender = textoaprender.substring(1);
                }
                showtexttolearnTxtView.setText("I said: " + textoaprender);
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
            String contvisto = leccionencurso.getNivelFkEnCont() + "a" +
                    leccionencurso.getLeccionFkEnCont() + "b" +
                    leccionencurso.getContenidoEnCont() + "c";

            String tablename = "usuario_table";
            String contvistocolumn = "contenido_visto";
            String nuevocontvisto = contvisto;
            String whereclause = "nombre_usuario = ?";
            String[] nomusuario = {usuario.getNomUsuario()};

            ContentValues values = new ContentValues();
            values.put(contvistocolumn, nuevocontvisto);
            SQLiteDatabase writableidiommazDb = idiommazDb.getWritableDatabase();

            writableidiommazDb.update(tablename, values, whereclause, nomusuario);

            idiommazDb.close();

                Toast.makeText(MainActivity.this, "El contenido aprendido se ha guardado",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //Material a enseñar
    public String obtenerContenido(String boton){
        int ultimoindiceinteger = Integer.parseInt(ultimoindice);
        switch (boton) {
                case "volverescuchar":
                    break;
                case "continuar":

                    centerimageImgView.setImageResource(0);
                    if(cont==ultimoindiceinteger-2 && leccionencurso.getFilasEnCont().isLast()){
                        String[] frasesfindeleccion = {"No more lessons", "Finished all lessons",
                                "Finnitto", "Konnek", "No I más lecciones", "No more", "Hasta la vista baby"};
                        int random = new Random().nextInt(7);
                        textoaprender=frasesfindeleccion[random];
                    }else if(cont==ultimoindiceinteger-1 && leccionencurso.getFilasEnCont().isLast()==false){
                        Toast.makeText(this, "Terminó una lección!",
                                Toast.LENGTH_LONG).show();
                        leccionencurso.getFilasEnCont().moveToNext();
                        cont=1;
                        lessonPgrsBr.setProgress(cont);
                        lessonprogressTxtView.setText("Lesson Progress " + cont + "%");
                        contbruto = leccionencurso.getFilasEnCont().getString(1);
                        ultimoindice = leccionencurso.getFilasEnCont().getString(3);
                        Toast.makeText(this, "Comenzó una lección nueva!",
                                Toast.LENGTH_LONG).show();
                    }else{
                        cont += 1; //Recordar restablecer valores de cont ya que está como variable global
                        Log.d("Main", "Cont en continuar es: " + cont);
                        textoaprender = contbruto.substring(contbruto.indexOf(Integer.toString(cont)),
                                contbruto.indexOf(Integer.toString(cont+1)));
                        // Todo: Mejorar lógica de estas dos condicionales para saber cuándo Índice es
                        // Todo: múltiplo de 10 (es decir, que aumente dígitos)
                        if(cont>9){
                            textoaprender = textoaprender.substring(2);
                        }
                        if(cont>99){
                            textoaprender = textoaprender.substring(3);
                        } else textoaprender = textoaprender.substring(1);
                        lessonprogress = (double) cont/(ultimoindiceinteger-1)*100;
                        lessonPgrsBr.setProgress((int) lessonprogress);
                        lessonprogressTxtView.setText("Lesson Progress " + (int) lessonprogress + "%");
                        regresarBtn.setEnabled(true);
                    }
                    break;
                case "regresar":

                    if(cont==0 && leccionencurso.getFilasEnCont().isFirst()) {
                        String[] frasesfindeleccion = {"Este es el inicio de la primera lección",
                        "This is the beginning of the first lesson"};
                        int random = new Random().nextInt(2);
                        textoaprender = frasesfindeleccion[random];
                        lessonprogress = (double) cont/(ultimoindiceinteger-1)*100;
                        lessonPgrsBr.setProgress((int) lessonprogress);
                        lessonprogressTxtView.setText("Lesson Progress " + (int) lessonprogress + "%");
                    }else if(cont==0 && leccionencurso.getFilasEnCont().isFirst()==false){
                        Toast.makeText(this, "Lección previa",
                                Toast.LENGTH_LONG).show();
                        leccionencurso.getFilasEnCont().moveToPrevious();
                        contbruto = leccionencurso.getFilasEnCont().getString(1);
                        ultimoindice = leccionencurso.getFilasEnCont().getString(3);
                        ultimoindiceinteger = Integer.parseInt(ultimoindice);
                        cont=ultimoindiceinteger;
                        textoaprender = contbruto.substring(contbruto.indexOf(Integer.toString(cont-1)),
                                contbruto.indexOf(Integer.toString(cont))).substring(1);
                        lessonprogress = (double) cont/(ultimoindiceinteger)*100;
                        lessonPgrsBr.setProgress((int) lessonprogress);
                        lessonprogressTxtView.setText("Lesson Progress " + (int) lessonprogress + "%");
                        cont-=1;
                    }else{
                        textoaprender = contbruto.substring(contbruto.indexOf(Integer.toString(cont-1)),
                                contbruto.indexOf(Integer.toString(cont)));
                        if(cont>9){
                            textoaprender = textoaprender.substring(2);
                        }
                        if(cont>99){
                            textoaprender = textoaprender.substring(3);
                        } else textoaprender = textoaprender.substring(1);
                        lessonprogress = (double) cont/(ultimoindiceinteger-1)*100;
                        lessonPgrsBr.setProgress((int) lessonprogress);
                        lessonprogressTxtView.setText("Lesson Progress " + (int) lessonprogress + "%");
                        cont-=1;
                    }
                    break;
            }

        if(textoaprender==null){
            Log.d("Main", "Textoaprender no tiene valor asignado");
            return "Asignar valor a texto aprender";
        } else {
            volveraescucharBtn.setEnabled(true);
            continuarBtn.setEnabled(true);
            tuturnoBtn.setEnabled(true);
            Log.d("Main", "Textoaprender por si no tengo dónde escuchar " + textoaprender +
                    " Cont " + cont);

            leccionencurso.setNivelFkEnCont(leccionencurso.getFilasEnCont().getString(4));
            leccionencurso.setLeccionFkEnCont(leccionencurso.getFilasEnCont().getString(2));
            leccionencurso.setContenidoEnCont(Integer.toString(cont));
            showtexttolearnTxtView.setText("");
            speechToTxtTxtView.setText("IDIOMMAZ");

            return textoaprender.substring(1);
        }
    }

    public void insertNivelInDB(){
        Cursor hayniveles = idiommazDb.getNiveles();
        if(hayniveles.getCount()==0){
            idiommazDb.insertNIVEL(nivela1);
            idiommazDb.insertNIVEL(nivela2);
            idiommazDb.insertNIVEL(nivelb1);
            idiommazDb.insertNIVEL(nivelb2);
            idiommazDb.insertNIVEL(nivelc1);
            idiommazDb.insertNIVEL(nivelc2);
        }else return;
    }

    public void insertLeccionInDb(){
        Cursor haylecciones = idiommazDb.getLecciones();

        if(haylecciones.getCount()==0){
            idiommazDb.insertLeccion(leccion1);
            idiommazDb.insertLeccion(leccion2);
        }else return;
    }

    public void insertContenidoInDb(){
        Cursor haycontenido = idiommazDb.getContenidos();

        if(haycontenido.getCount() == 0){
            idiommazDb.insertContenido(contenido1);
            idiommazDb.insertContenido(contenido2);
            idiommazDb.insertContenido(contenido3);
        }
    }

    private void speak(String boton){
        String text;
        if(boton.matches("comenzar")){
            text = textoaprender;
        }else text = obtenerContenido(boton);

        float pitch = (float) pitchSeekBr.getProgress() / 50;
        if(pitch < 0.1) pitch = 0.1f;
        float speed = (float) speedSeekBr.getProgress() / 50;
        if(speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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

                    String textoaprendertrim = textoaprender.trim();

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
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}

