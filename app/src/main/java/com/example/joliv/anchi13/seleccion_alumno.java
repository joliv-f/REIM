package com.example.joliv.anchi13;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class seleccion_alumno extends AppCompatActivity {

    Button iniciar_actividad;
    ProgressBar pbbar_alumno;

    ArrayList<String> coleccion = new ArrayList<>();
    ArrayAdapter<String> adaptador;
    ListView lista;
    seleccion_curso seleccion_curso = new seleccion_curso();
    login login = new login();

    static String elemento_lista_alumno;
    static int id_curso;
    static String nombre_alumno, nombre_2;
    static String apellido_pa_alumno;
    static String apellido_ma_alumno;
    static String fecha_inicio;
    Fecha f = new Fecha();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_alumno);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lista = (ListView) findViewById(R.id.lista2);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coleccion);
        lista.setAdapter(adaptador);


        pbbar_alumno = (ProgressBar) findViewById(R.id.progressBar_alumno);
        pbbar_alumno.setVisibility(View.GONE);

        consulta query = new consulta();
        query.execute();


        iniciar_actividad = (Button) findViewById(R.id.iniciar_actividad);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                elemento_lista_alumno = String.valueOf(parent.getItemAtPosition(position));
                String partes[] = elemento_lista_alumno.split("\\s+");

                if(partes.length < 4){
                    nombre_alumno = partes[0];
                    apellido_pa_alumno = partes[1];
                    apellido_ma_alumno = partes[2];
                }else{
                    nombre_alumno = partes[0];
                    nombre_2 = partes[1];
                    apellido_pa_alumno = partes[2];
                    apellido_ma_alumno = partes[3];
                    nombre_alumno = nombre_alumno + " " + nombre_2;
                }
/*
                nombre_alumno = partes[0];
                apellido_pa_alumno = partes[1];
                apellido_ma_alumno = partes[2];
*/
                Toast.makeText(seleccion_alumno.this, "Selecciono al Alumno: " + elemento_lista_alumno, Toast.LENGTH_LONG).show();
                String[] splitStrings = elemento_lista_alumno.split(" ");

            }
        });
        iniciar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elemento_lista_alumno != null) {
                    fecha_inicio = f.fecchai;
                    Intent intent = new Intent(seleccion_alumno.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


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

    public class consulta extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            pbbar_alumno.setVisibility(View.VISIBLE);
        }


        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            pbbar_alumno.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                String query2 = "select id from curso where nombre ='" + seleccion_curso.elemento_lista_curso + "' ";
                Statement stm2 = c.prepareStatement(query2);
                ResultSet rs2 = stm2.executeQuery(query2);

                if (rs2.next()) {
                    id_curso = rs2.getInt("id");
                    System.out.println(id_curso);
                }

                System.out.println("id_curso===" + id_curso);

                String query = "select u.nombres, u.apellido_paterno, u.apellido_materno from sf_guard_user u, PERTENECE_reim p, curso c where c.id='" + id_curso + "' and p.CURSO_id_curso='" + id_curso + "' and u.id=p.sf_guard_user_id and u.alumno !=0";
                Statement stm = c.prepareStatement(query);
                ResultSet rs = stm.executeQuery(query);

                while (rs.next()) {
                    String nombre = rs.getString("nombres");
                    String apellido_pa = rs.getString("apellido_paterno");
                    String apellido_ma = rs.getString("apellido_materno");
                    coleccion.add(nombre + " " + apellido_pa + " " + apellido_ma);
                }


                c.close();//cierra conexion
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();

            }

            return null;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_UP):
                adaptador.notifyDataSetChanged();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
