package com.example.joliv.anchi13;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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

public class actividad_animal3_zonasur extends AppCompatActivity implements View.OnClickListener {

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
    ProgressBar pbbar;
    int usuario = 100;
    int animal = 9;
    int item = 1;
    int time = 0;
    int alternativa = 0;
    int audio = 0;
    int es_correcta = 0;
    int aux = 1;
    String fechaBoton;
    String fechaInicioActividad;
    String fechaTermino;
    Fecha f = new Fecha();
    MainActivity m = new MainActivity();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    MediaPlayer respCorrecta;
    MediaPlayer respIncorrecta;
    MediaPlayer toca_azul, alimentacion, desplazamiento, piel;

    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();


    Date utilDate = new Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    Tiempo tiempo = new Tiempo();

    String query1 = "INSERT INTO ALUMNO_REALIZA_ACTIVIDAD (datetime_inicio_actividad, datetime_touch, datetime_termino_actividad, correcto, ACTIVIDAD_id_actividad, ASIGNA_REALIZAR_SESION_id_sesion, resultado, contador_click_instrucciones)"
            + "VALUES (?,?,?,?,?,?, 'oveja', ?)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_animal3_zonasur);

        fechaInicioActividad = f.fecchai;

        respCorrecta = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.rescorrecta);
        respIncorrecta = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.resincorrecta);
        toca_azul = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.azul);
        alimentacion = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.oveja_alimento);
        desplazamiento = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.oveja_desplazamiento);
        piel = MediaPlayer.create(actividad_animal3_zonasur.this, R.raw.oveja_piel);


        list.add(R.drawable.carne);
        list.add(R.drawable.pez);
        list.add(R.drawable.fruta);
        list.add(R.drawable.pasto);

        list1.add(R.drawable.patas);
        list1.add(R.drawable.patas_pato);
        list1.add(R.drawable.alas);
        list1.add(R.drawable.nadar);

        list2.add(R.drawable.pieltigre);
        list2.add(R.drawable.piel_oveja);
        list2.add(R.drawable.piel_lobomarino);
        list2.add(R.drawable.piel_serpiente);


        tiempo.Contar();

        opcion1 = (ImageButton) findViewById(R.id.alternativa1_zonasur_3);
        opcion2 = (ImageButton) findViewById(R.id.alternativa2_zonasur_3);
        opcion3 = (ImageButton) findViewById(R.id.alternativa3_zonasur_3);
        opcion4 = (ImageButton) findViewById(R.id.alternativa4_zonasur_3);
        comparar = (ImageButton) findViewById(R.id.comparar_sur3);
        comparar1 = (ImageButton) findViewById(R.id.comparar1_sur3);
        comparar2 = (ImageButton) findViewById(R.id.comparar2_sur3);
        salir = (ImageButton) findViewById(R.id.salir_animal3_zonasur);
        medalla = (TextView) findViewById(R.id.medallas_sur3);
        boton_audio = (ImageButton) findViewById(R.id.reproComidaSur3);

        pbbar = (ProgressBar) findViewById(R.id.pbbar_oveja);
        pbbar.setVisibility(View.GONE);

        opcion1.setOnClickListener(this);
        opcion2.setOnClickListener(this);
        opcion3.setOnClickListener(this);
        opcion4.setOnClickListener(this);
        salir.setOnClickListener(this);
        boton_audio.setOnClickListener(this);

        comparar.setBackgroundResource(R.drawable.pasto);
        comparar1.setBackgroundResource(R.drawable.patas);
        comparar2.setBackgroundResource(R.drawable.piel_oveja);
        medalla.setBackgroundResource(R.drawable.medallas1);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.alternativa1_zonasur_3:
                alternativa = 1;

                if (aux == 1) {

                    if (opcion1.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        respCorrecta.start();
                        es_correcta = 1;
                        item = 1;
                        consulta query_a1 = new consulta();
                        query_a1.execute();
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
                        consulta query1 = new consulta();
                        query1.execute();
                        aux = 1;
                        break;

                    }
                } else {

                    switch (aux) {

                        case 2:

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

                            if (opcion1.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {


                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                respCorrecta.start();
                                es_correcta = 1;
                                consulta c = new consulta();
                                item = 3;
                                c.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_animal3_zonasur.this, entrega_liston_oro_zonasur.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = f.fecchai;
                                fechaTermino = "0000-00-00 00:00:00";
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

            case R.id.alternativa2_zonasur_3:
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
                                respIncorrecta.start();
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
                                Intent intent = new Intent(actividad_animal3_zonasur.this, entrega_liston_oro_zonasur.class);
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

            case R.id.alternativa3_zonasur_3:
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
                                respCorrecta.start();
                                es_correcta = 1;
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
                                Intent intent = new Intent(actividad_animal3_zonasur.this, entrega_liston_oro_zonasur.class);
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

            case R.id.alternativa4_zonasur_3:
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
                                Intent intent = new Intent(actividad_animal3_zonasur.this, entrega_liston_oro_zonasur.class);
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

            case R.id.salir_animal3_zonasur:

                Intent intent = new Intent(actividad_animal3_zonasur.this, MainActivity.class);
                startActivity(intent);
                if (toca_azul.isPlaying()){
                    toca_azul.release();
                }
                onStop();
                break;

            case R.id.reproComidaSur3:

                audio++;

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

    private class consulta extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pbbar.setVisibility(View.VISIBLE);

            opcion1.setClickable(false);
            opcion2.setClickable(false);
            opcion3.setClickable(false);
            opcion4.setClickable(false);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pbbar.setVisibility(View.GONE);
            opcion1.setClickable(true);
            opcion2.setClickable(true);
            opcion3.setClickable(true);
            opcion4.setClickable(true);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";//"jdbc:mysql:///10.0.3.2:3306/dbname"
                Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

//datetime_inicio_actividad, datetime_touch, datetime_termino_actividad, correcto, ACTIVIDAD_id_actividad, ASIGNA_REALIZAR_SESION_id_sesion

                PreparedStatement st = c.prepareStatement(query1);


                st.setTimestamp(1, Timestamp.valueOf(fechaInicioActividad));
                st.setTimestamp(2, Timestamp.valueOf(fechaBoton));
                st.setTimestamp(3, Timestamp.valueOf(fechaTermino));
                st.setInt(4, es_correcta);
                st.setInt(5, item);
                st.setInt(6, m.id_sesion);
                st.setInt(7, audio);

                st.execute();
                st.close();
                c.close();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();

            }

            return null;
        }
    }

}

