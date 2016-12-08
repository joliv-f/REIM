package com.example.joliv.anchi13;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class actividad_animal3_zonanorte extends AppCompatActivity implements View.OnClickListener {

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
    int time = 0;
    int alternativa = 0;
    int aux = 1;
    int es_correcta = 0;
    int item = 1;
    int usuario = 1;
    int animal = 1;
    int audio = 0;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String fechaBoton;
    String fechaInicioActividad;
    String fechaTermino;
    Fecha f = new Fecha();
    MainActivity m = new MainActivity();

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
            + "VALUES (?,?,?,?,?,?, 'flamenco', ?)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_animal3_zonanorte);

        fechaInicioActividad = f.fecchai;

        respCorrecta = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.rescorrecta);
        respIncorrecta = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.resincorrecta);
        toca_azul = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.azul);
        alimentacion = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.flamenco_alimento);
        desplazamiento = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.flamenco_desplazamiento);
        piel = MediaPlayer.create(actividad_animal3_zonanorte.this, R.raw.flamenco_piel);

        list.add(R.drawable.carne);
        list.add(R.drawable.alga);
        list.add(R.drawable.pez);
        list.add(R.drawable.mosca);

        list1.add(R.drawable.patas_rana);
        list1.add(R.drawable.patas);
        list1.add(R.drawable.alas);
        list1.add(R.drawable.nadar);

        list2.add(R.drawable.pieltigre);
        list2.add(R.drawable.piel_flamenco);
        list2.add(R.drawable.piel_oveja);
        list2.add(R.drawable.piel_rana);


        tiempo.Contar();

        opcion1 = (ImageButton) findViewById(R.id.alternativa1_zonanorte_3);
        opcion2 = (ImageButton) findViewById(R.id.alternativa2_zonanorte_3);
        opcion3 = (ImageButton) findViewById(R.id.alternativa3_zonanorte_3);
        opcion4 = (ImageButton) findViewById(R.id.alternativa4_zonanorte_3);
        comparar = (ImageButton) findViewById(R.id.comparar_norte3);
        comparar1 = (ImageButton) findViewById(R.id.comparar1_norte3);
        comparar2 = (ImageButton) findViewById(R.id.comparar2_norte3);
        salir = (ImageButton) findViewById(R.id.salir_animal3_zonanorte);
        medalla = (TextView) findViewById(R.id.medallas_norte3);
        boton_audio = (ImageButton) findViewById(R.id.reproComidaNorte3);

        pbbar = (ProgressBar) findViewById(R.id.pbbar_flamenco);
        pbbar.setVisibility(View.GONE);

        opcion1.setOnClickListener(this);
        opcion2.setOnClickListener(this);
        opcion3.setOnClickListener(this);
        opcion4.setOnClickListener(this);
        salir.setOnClickListener(this);
        boton_audio.setOnClickListener(this);

        comparar.setBackgroundResource(R.drawable.alga);
        comparar1.setBackgroundResource(R.drawable.alas);
        comparar2.setBackgroundResource(R.drawable.piel_flamenco);


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

            case R.id.alternativa1_zonanorte_3:
                alternativa = 1;

                if (aux == 1) {

                    if (opcion1.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {
                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        es_correcta = 1;
                        item = 1;
                        respCorrecta.start();
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
                        es_correcta = 0;
                        item = 1;
                        consulta query1 = new consulta();
                        query1.execute();
                        respIncorrecta.start();
                        aux = 1;
                        break;

                    }
                } else {

                    switch (aux) {

                        case 2:

                            if (opcion1.getBackground().getConstantState().equals(comparar1.getBackground().getConstantState())) {


                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                respCorrecta.start();
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
                                es_correcta = 0;
                                item = 2;
                                respIncorrecta.start();
                                consulta b = new consulta();
                                b.execute();
                                aux = 2;
                                break;

                            }
                        case 3:

                            if (opcion1.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                respCorrecta.start();
                                consulta c = new consulta();
                                item = 3;
                                c.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_animal3_zonanorte.this, entrega_liston_oro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 0;
                                item = 3;
                                respIncorrecta.start();
                                consulta d = new consulta();
                                d.execute();
                                aux = 3;
                                break;

                            }


                    }

                }
                break;

            case R.id.alternativa2_zonanorte_3:
                alternativa = 2;

                if (aux == 1) {

                    if (opcion2.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        es_correcta = 1;
                        respCorrecta.start();
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
                        es_correcta = 0;
                        respIncorrecta.start();
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
                                es_correcta = 1;
                                item = 2;
                                respCorrecta.start();
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
                                respIncorrecta.start();
                                consulta h = new consulta();
                                h.execute();
                                aux = 2;
                                break;

                            }

                        case 3:

                            if (opcion2.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                item = 3;
                                respCorrecta.start();
                                consulta i = new consulta();
                                i.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_animal3_zonanorte.this, entrega_liston_oro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 0;
                                item = 3;
                                respIncorrecta.start();
                                consulta j = new consulta();
                                j.execute();
                                aux = 3;
                                break;

                            }

                    }


                }
                break;

            case R.id.alternativa3_zonanorte_3:
                alternativa = 3;

                if (aux == 1) {

                    if (opcion3.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {
                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        es_correcta = 1;
                        item = 1;
                        respCorrecta.start();
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
                        es_correcta = 0;
                        item = 1;
                        respIncorrecta.start();
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
                                item = 2;
                                respCorrecta.start();
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
                                es_correcta = 0;
                                item = 2;
                                respIncorrecta.start();
                                consulta consulta3 = new consulta();
                                consulta3.execute();
                                aux = 2;
                                break;


                            }

                        case 3:

                            if (opcion3.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                item = 3;
                                respCorrecta.start();
                                consulta consulta3 = new consulta();
                                consulta3.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_animal3_zonanorte.this, entrega_liston_oro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 0;
                                item = 3;
                                respIncorrecta.start();
                                consulta consulta4 = new consulta();
                                consulta4.execute();
                                break;

                            }


                    }

                }
                break;

            case R.id.alternativa4_zonanorte_3:
                alternativa = 4;

                if (aux == 1) {

                    if (opcion4.getBackground().getConstantState().equals(comparar.getBackground().getConstantState())) {

                        fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                        fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                        es_correcta = 1;
                        item = 1;
                        respCorrecta.start();
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
                        es_correcta = 0;
                        item = 1;
                        respIncorrecta.start();
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
                                es_correcta = 1;
                                respCorrecta.start();
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
                                es_correcta = 0;
                                item = 2;
                                respIncorrecta.start();
                                consulta b = new consulta();
                                b.execute();
                                aux = 2;
                                break;

                            }

                        case 3:

                            if (opcion4.getBackground().getConstantState().equals(comparar2.getBackground().getConstantState())) {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 1;
                                respCorrecta.start();
                                consulta c = new consulta();
                                item = 3;
                                c.execute();
                                medalla.setBackgroundResource(R.drawable.medallas4);
                                Intent intent = new Intent(actividad_animal3_zonanorte.this, entrega_liston_oro.class);
                                startActivity(intent);
                                break;

                            } else {

                                fechaBoton = (dateFormat.format(Calendar.getInstance().getTime()));
                                fechaTermino = (dateFormat.format(Calendar.getInstance().getTime()));
                                es_correcta = 0;
                                item = 3;
                                respIncorrecta.start();
                                consulta d = new consulta();
                                d.execute();
                                break;

                            }

                    }

                }
                break;

            case R.id.salir_animal3_zonanorte:

                Intent intent = new Intent(actividad_animal3_zonanorte.this, MainActivity.class);
                startActivity(intent);
                if (toca_azul.isPlaying()){
                    toca_azul.release();
                }
                onStop();
                break;

            case R.id.reproComidaNorte3:

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

            opcion1.setEnabled(false);
            opcion2.setEnabled(false);
            opcion3.setEnabled(false);
            opcion4.setEnabled(false);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
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
