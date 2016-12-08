package com.example.joliv.anchi13;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class actividad_amimal1_zonacentro extends AppCompatActivity implements View.OnClickListener {


    ImageButton opcion1;
    ImageButton opcion2;
    ImageButton opcion3;
    ImageButton opcion4;
    ImageButton comparar;
    ImageButton comparar1;
    ImageButton comparar2;
    ImageButton salir;
    ImageButton boton_audio;
    TextView medalla;
    int usuario = 100;
    int audio_aliemnto = 0;
    int item = 1;
    int alternativa = 0;
    int audio = 0;
    int es_correcta = 0;
    int aux = 1;

    ProgressBar pbbar;

    String fechaBoton;
    String fechaInicioActividad;
    String fechaTermino;

    Fecha f = new Fecha();
    MainActivity m = new MainActivity();

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    MediaPlayer alimentacion, desplazamiento, piel;
    MediaPlayer respCorrecta;
    MediaPlayer respIncorrecta;
    MediaPlayer toca_azul;
    MediaPlayer sonidoMedalla = new MediaPlayer();

    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();


    //Query que utilizo para insertar en la tabla alumno_realiza_actividad
    String query1 = "INSERT INTO ALUMNO_REALIZA_ACTIVIDAD (datetime_inicio_actividad, datetime_touch, datetime_termino_actividad, correcto, ACTIVIDAD_id_actividad, ASIGNA_REALIZAR_SESION_id_sesion, resultado, contador_click_instrucciones)"
            + "VALUES (?,?,?,?,?,?, 'condor', ?)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_amimal1_zonacentro);

        //fecha y hora al abrir la actividad
        fechaInicioActividad = f.fecchai;

//Sonidos
        respCorrecta = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.rescorrecta);
        respIncorrecta = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.resincorrecta);
        sonidoMedalla = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.sonidomedalla);
        alimentacion = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.condor_alimento);
        desplazamiento = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.condor_desplazamiento);
        piel = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.condor_piel);
        toca_azul = MediaPlayer.create(actividad_amimal1_zonacentro.this, R.raw.azul);


//Agrego elementos a distintas listas para despues sacarlos y mostrar las alternativas random
        list.add(R.drawable.pasto);
        list.add(R.drawable.fruta);
        list.add(R.drawable.carrona);
        list.add(R.drawable.pez);

        list1.add(R.drawable.patas_pato);
        list1.add(R.drawable.cola);
        list1.add(R.drawable.alas);
        list1.add(R.drawable.nadar);

        list2.add(R.drawable.pieltigre);
        list2.add(R.drawable.piel_condor);
        list2.add(R.drawable.piel_oveja);
        list2.add(R.drawable.piel_serpiente);

        //Botones
        opcion1 = (ImageButton) findViewById(R.id.alternativa1_zonacentro);
        opcion2 = (ImageButton) findViewById(R.id.alternativa2_zonacentro);
        opcion3 = (ImageButton) findViewById(R.id.alternativa3_zonacentro);
        opcion4 = (ImageButton) findViewById(R.id.alternativa4_zonacentro);
        comparar = (ImageButton) findViewById(R.id.comparar);
        comparar1 = (ImageButton) findViewById(R.id.comparar1);
        comparar2 = (ImageButton) findViewById(R.id.comparar2);
        salir = (ImageButton) findViewById(R.id.salir_animal1_zonacentro);
        medalla = (TextView) findViewById(R.id.medallas_centro1);
        boton_audio = (ImageButton) findViewById(R.id.reproComidaCentro1);


        pbbar = (ProgressBar) findViewById(R.id.pbbar_condor);
        pbbar.setVisibility(View.GONE);

        toca_azul.start();

        opcion1.setOnClickListener(this);
        opcion2.setOnClickListener(this);
        opcion3.setOnClickListener(this);
        opcion4.setOnClickListener(this);
        salir.setOnClickListener(this);
        boton_audio.setOnClickListener(this);


        //botones invisibles con las alternativas correctas, despues los ocupo para comparar con las alternativas que selecciona el niño
        comparar.setBackgroundResource(R.drawable.carrona);
        comparar1.setBackgroundResource(R.drawable.alas);
        comparar2.setBackgroundResource(R.drawable.piel_condor);
        medalla.setBackgroundResource(R.drawable.medallas1);


        //saco las imagenes de la lista de forma aleatoria, luego las seteo en las alternativas y elimino el elemento de la lista para no repetir la imagen
        int position = new Random().nextInt(list.size());
        opcion1.setBackgroundResource(list.get(position));
        list.remove(position);
        Collections.shuffle(list);

        int position1 = new Random().nextInt(list.size());
        opcion2.setBackgroundResource(list.get(position1));
        list.remove(position1);
        Collections.shuffle(list);

        int position2 = new Random().nextInt(list.size());
        opcion3.setBackgroundResource(list.get(position2));
        list.remove(position2);

        opcion4.setBackgroundResource(list.get(0));


        toca_azul.start();


    }

    //ACCIONES QUE SE REALIZAN AL TOCAR LOS BOTONES DE LA ACTIVIDAD

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //en caso que seleccione la alternativa 1

            case R.id.alternativa1_zonacentro://alternativa 1
                alternativa = 1;
                //variable auxiliar que me indica en que actividad estoy (1 alimentacion, 2 desplazamiento, 3 alimentacion)
                //si estoy en la actividad uno sucede lo siguiente
                if (aux == 1) {

                    //si la opcion que se clickeo contiene el mismo background que la alternativa correcta, que se enuentra invisible, entra, en otras palabras si la alternativa seleccionada
                    //es la correcta, entra al if
                    if (opcion1.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        //fecha del boton al ser clickeado
                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        //fecha de termino de la actividad
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        //se reproduce el sonido de respuesta correcta
                        respCorrecta.start();
                        es_correcta = 1;
                        item = 1;//item es igual a actividad
                        consulta query_a1 = new consulta();//luego de obtener todos los datos, creo un objeto de la clase consulta

                        //executo la query con los valores que obtengo
                        query_a1.execute();

                        //seteo la medalla de bronce
                        medalla.setBackgroundResource(R.drawable.medallas2);

                        // cambio los background de los botones para poner las alternativas de desplazamiento
                        int position = new Random().nextInt(list1.size());
                        opcion1.setBackgroundResource(list1.get(position));
                        list1.remove(position);
                        Collections.shuffle(list1);

                        int position1 = new Random().nextInt(list1.size());
                        opcion2.setBackgroundResource(list1.get(position1));
                        list1.remove(position1);
                        Collections.shuffle(list1);

                        int position2 = new Random().nextInt(list1.size());
                        opcion3.setBackgroundResource(list1.get(position2));
                        list1.remove(position2);

                        opcion4.setBackgroundResource(list1.get(0));

                        aux = 2;//paso a la actividad 2

                        break;

                    } else {//si es incorrecto

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respIncorrecta.start();
                        es_correcta = 0;
                        item = 1;
                        consulta query1 = new consulta();
                        query1.execute();
                        aux = 1;
                        break;

                    }
                } else {//si estoy en la actividad 2

                    switch (aux) {

                        case 2:

                            //correcto
                            if (opcion1.getBackground().getConstantState().equals(comparar1.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                consulta a = new consulta();
                                item = 2;
                                a.execute();
                                medalla.setBackgroundResource(R.drawable.medallas3);

                                int position = new Random().nextInt(list2.size());
                                opcion1.setBackgroundResource(list2.get(position));
                                list2.remove(position);
                                Collections.shuffle(list2);

                                int position1 = new Random().nextInt(list2.size());
                                opcion2.setBackgroundResource(list2.get(position1));
                                list2.remove(position1);
                                Collections.shuffle(list2);

                                int position2 = new Random().nextInt(list2.size());
                                opcion3.setBackgroundResource(list2.get(position2));
                                list2.remove(position2);

                                opcion4.setBackgroundResource(list2.get(0));

                                aux = 3;
                                break;
                            } else {//incorrecto

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 2;
                                consulta b = new consulta();
                                b.execute();
                                aux = 2;
                                break;

                            }
                        case 3://actividad 3

                            //correcto
                            if (opcion1.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                consulta c = new consulta();
                                item = 3;
                                c.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_amimal1_zonacentro.this, entrega_liston_oro_zonacentro.class);
                                startActivity(intent);
                                break;

                            } else {//incorrecto

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 3;
                                consulta d = new consulta();
                                d.execute();
                                aux = 3;
                                break;

                            }


                    }

                }
                break;

            case R.id.alternativa2_zonacentro://alternativa 2
                alternativa = 2;

                if (aux == 1) {

                    if (opcion2.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respCorrecta.start();
                        es_correcta = 1;
                        consulta e = new consulta();
                        item = 1;
                        e.execute();
                        medalla.setBackgroundResource(R.drawable.medallas2);
                        int position = new Random().nextInt(list1.size());
                        opcion1.setBackgroundResource(list1.get(position));
                        list1.remove(position);
                        Collections.shuffle(list1);

                        int position1 = new Random().nextInt(list1.size());
                        opcion2.setBackgroundResource(list1.get(position1));
                        list1.remove(position1);
                        Collections.shuffle(list1);

                        int position2 = new Random().nextInt(list1.size());
                        opcion3.setBackgroundResource(list1.get(position2));
                        list1.remove(position2);

                        opcion4.setBackgroundResource(list1.get(0));

                        aux = 2;
                        break;

                    } else {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respIncorrecta.start();
                        es_correcta = 0;
                        consulta f = new consulta();
                        f.execute();
                        aux = 1;
                        break;

                    }

                } else {

                    switch (aux) {

                        case 2:

                            if (opcion2.getBackground().getConstantState().equals(comparar1.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                item = 2;
                                consulta g = new consulta();
                                g.execute();
                                medalla.setBackgroundResource(R.drawable.medallas3);
                                int position = new Random().nextInt(list2.size());
                                opcion1.setBackgroundResource(list2.get(position));
                                list2.remove(position);
                                Collections.shuffle(list2);

                                int position1 = new Random().nextInt(list2.size());
                                opcion2.setBackgroundResource(list2.get(position1));
                                list2.remove(position1);
                                Collections.shuffle(list2);

                                int position2 = new Random().nextInt(list2.size());
                                opcion3.setBackgroundResource(list2.get(position2));
                                list2.remove(position2);

                                opcion4.setBackgroundResource(list2.get(0));

                                aux = 3;
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 0;
                                item = 2;
                                consulta h = new consulta();
                                h.execute();
                                aux = 2;
                                break;

                            }

                        case 3:

                            if (opcion2.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                item = 3;
                                consulta i = new consulta();
                                i.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_amimal1_zonacentro.this, entrega_liston_oro_zonacentro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 3;
                                consulta j = new consulta();
                                j.execute();
                                aux = 3;
                                break;

                            }

                    }


                }
                break;

            case R.id.alternativa3_zonacentro://alernativa 3
                alternativa = 3;

                if (aux == 1) {

                    if (opcion3.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respCorrecta.start();
                        es_correcta = 1;
                        item = 1;
                        consulta consulta = new consulta();
                        consulta.execute();
                        medalla.setBackgroundResource(R.drawable.medallas2);
                        int position = new Random().nextInt(list1.size());
                        opcion1.setBackgroundResource(list1.get(position));
                        list1.remove(position);
                        Collections.shuffle(list1);

                        int position1 = new Random().nextInt(list1.size());
                        opcion2.setBackgroundResource(list1.get(position1));
                        list1.remove(position1);
                        Collections.shuffle(list1);

                        int position2 = new Random().nextInt(list1.size());
                        opcion3.setBackgroundResource(list1.get(position2));
                        list1.remove(position2);

                        opcion4.setBackgroundResource(list1.get(0));

                        aux = 2;
                        break;

                    } else {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respIncorrecta.start();
                        es_correcta = 0;
                        item = 1;
                        consulta consulta1 = new consulta();
                        consulta1.execute();
                        aux = 1;
                        break;

                    }

                } else {

                    switch (aux) {

                        case 2:

                            if (opcion3.getBackground().getConstantState().equals(comparar1.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                respCorrecta.start();
                                item = 2;
                                consulta consulta2 = new consulta();
                                consulta2.execute();
                                medalla.setBackgroundResource(R.drawable.medallas3);
                                int position = new Random().nextInt(list2.size());
                                opcion1.setBackgroundResource(list2.get(position));
                                list2.remove(position);
                                Collections.shuffle(list2);

                                int position1 = new Random().nextInt(list2.size());
                                opcion2.setBackgroundResource(list2.get(position1));
                                list2.remove(position1);
                                Collections.shuffle(list2);

                                int position2 = new Random().nextInt(list2.size());
                                opcion3.setBackgroundResource(list2.get(position2));
                                list2.remove(position2);

                                opcion4.setBackgroundResource(list2.get(0));

                                aux = 3;
                                break;


                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 2;
                                consulta consulta3 = new consulta();
                                consulta3.execute();
                                aux = 2;
                                break;


                            }

                        case 3:

                            if (opcion3.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                item = 3;
                                consulta consulta3 = new consulta();
                                consulta3.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_amimal1_zonacentro.this, entrega_liston_oro_zonacentro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 3;
                                consulta consulta4 = new consulta();
                                consulta4.execute();
                                break;

                            }


                    }

                }
                break;

            case R.id.alternativa4_zonacentro://alternativa 4
                alternativa = 4;

                if (aux == 1) {

                    if (opcion4.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respCorrecta.start();
                        es_correcta = 1;
                        item = 1;
                        consulta consulta5 = new consulta();
                        consulta5.execute();
                        medalla.setBackgroundResource(R.drawable.medallas2);
                        int position = new Random().nextInt(list1.size());
                        opcion1.setBackgroundResource(list1.get(position));
                        list1.remove(position);
                        Collections.shuffle(list1);

                        int position1 = new Random().nextInt(list1.size());
                        opcion2.setBackgroundResource(list1.get(position1));
                        list1.remove(position1);
                        Collections.shuffle(list1);

                        int position2 = new Random().nextInt(list1.size());
                        opcion3.setBackgroundResource(list1.get(position2));
                        list1.remove(position2);

                        opcion4.setBackgroundResource(list1.get(0));

                        aux = 2;
                        break;

                    } else {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respIncorrecta.start();
                        es_correcta = 0;
                        item = 1;
                        consulta consulta6 = new consulta();
                        consulta6.execute();
                        aux = 1;
                        break;
                    }

                } else {

                    switch (aux) {

                        case 2:

                            if (opcion4.getBackground().getConstantState().equals(comparar1.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                consulta a = new consulta();
                                item = 2;
                                a.execute();
                                medalla.setBackgroundResource(R.drawable.medallas3);
                                int position = new Random().nextInt(list2.size());
                                opcion1.setBackgroundResource(list2.get(position));
                                list2.remove(position);
                                Collections.shuffle(list2);

                                int position1 = new Random().nextInt(list2.size());
                                opcion2.setBackgroundResource(list2.get(position1));
                                list2.remove(position1);
                                Collections.shuffle(list2);

                                int position2 = new Random().nextInt(list2.size());
                                opcion3.setBackgroundResource(list2.get(position2));
                                list2.remove(position2);

                                opcion4.setBackgroundResource(list2.get(0));

                                aux = 3;
                                break;
                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 2;
                                consulta b = new consulta();
                                b.execute();
                                aux = 2;
                                break;

                            }

                        case 3:

                            if (opcion4.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                consulta c = new consulta();
                                item = 3;
                                c.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_amimal1_zonacentro.this, entrega_liston_oro_zonacentro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respIncorrecta.start();
                                es_correcta = 0;
                                item = 3;
                                consulta d = new consulta();
                                d.execute();
                                break;

                            }

                    }

                }
                break;

            case R.id.salir_animal1_zonacentro://boton salir

                Intent intent = new Intent(actividad_amimal1_zonacentro.this, MainActivity.class);
                startActivity(intent);
                if (toca_azul.isPlaying()){
                    toca_azul.release();
                }
                onStop();
                break;

            case R.id.reproComidaCentro1://boton de instrucciones


                if (aux == 1) {

                    alimentacion.start();
                }

                if (aux == 2) {

                    desplazamiento.start();
                }

                if (aux == 3) {

                    piel.start();
                }


                break;

        }
    }


    //boton de retroceso desactivado
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    //clase consulta donde se ejecutan las querys
    private class consulta extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {//desabilito los botones al estar ejecutandose la query
            super.onPreExecute();

            pbbar.setVisibility(View.VISIBLE);

            opcion1.setEnabled(false);
            opcion2.setEnabled(false);
            opcion3.setEnabled(false);
            opcion4.setEnabled(false);


        }

        @Override
        protected void onPostExecute(Void aVoid) {//los vuelvo a habilitar
            super.onPostExecute(aVoid);

            pbbar.setVisibility(View.GONE);

            opcion1.setEnabled(true);
            opcion2.setEnabled(true);
            opcion3.setEnabled(true);
            opcion4.setEnabled(true);


        }

        @Override
        protected Void doInBackground(Void... params) {


            try {

                //conexion
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";//"jdbc:mysql:///10.0.3.2:3306/dbname"
                Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                //declaro el statement con la query para despues ejecutarla
                PreparedStatement st = c.prepareStatement(query1);
                System.out.println(audio);


                // + "VALUES (?,?,?,?,?,?, 'condor', ?)";
                st.setTimestamp(1, Timestamp.valueOf(fechaInicioActividad));//en el primer ? inserto el valor
                st.setTimestamp(2, Timestamp.valueOf(fechaBoton));//en el segundo ? inserto el valor
                st.setTimestamp(3, Timestamp.valueOf(fechaTermino));//en el tercer ? inserto el valor
                st.setInt(4, es_correcta);//en el cuarto ? inserto el valor
                st.setInt(5, item);
                st.setInt(6, m.id_sesion);
                st.setInt(7, audio_aliemnto);

                st.execute();//se ejecuta la query
                st.close();//cierro el statement con la query
                c.close();//cierro la conexion

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();

            }


            return null;
        }
    }
}
