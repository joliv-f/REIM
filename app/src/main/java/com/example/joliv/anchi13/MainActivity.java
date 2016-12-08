package com.example.joliv.anchi13;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button zonaNorte;
    Button zonaCentro;
    Button zonaSur;
    static int id_sesion, id_pertenece, id_pertenece_tabla;
    static int empieza = 0;
    static int termina = 5;
    Fecha f = new Fecha();
    seleccion_alumno s = new seleccion_alumno();
    String fecha_termino;
    ImageButton salir;
    MediaPlayer bienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empieza++;
        consulta z = new consulta();
        z.execute();

        bienvenido = MediaPlayer.create(MainActivity.this, R.raw.bienvenido);

        salir = (ImageButton) findViewById(R.id.salir_menu_principal);


        zonaNorte = (Button) findViewById(R.id.zona1);
        zonaNorte.setVisibility(View.VISIBLE);
        zonaNorte.setBackgroundColor(Color.TRANSPARENT);
        zonaCentro = (Button) findViewById(R.id.zona2);
        zonaCentro.setVisibility(View.VISIBLE);
        zonaCentro.setBackgroundColor(Color.TRANSPARENT);
        zonaSur = (Button) findViewById(R.id.zona3);
        zonaSur.setVisibility(View.VISIBLE);
        zonaSur.setBackgroundColor(Color.TRANSPARENT);

        zonaNorte.setOnClickListener(this);
        zonaCentro.setOnClickListener(this);
        zonaSur.setOnClickListener(this);
        salir.setOnClickListener(this);


        bienvenido.start();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.zona1:
                Intent zonaNorte = new Intent(MainActivity.this, menu_zona_norte.class);
                startActivity(zonaNorte);
                if (bienvenido.isPlaying()){
                    bienvenido.release();
                }
                finish();
                break;
            case R.id.zona2:
                Intent zonaCentro = new Intent(MainActivity.this, menu_zona_centro.class);
                startActivity(zonaCentro);
                if (bienvenido.isPlaying()){
                    bienvenido.release();
                }
                finish();
                break;
            case R.id.zona3:
                Intent zonaSur = new Intent(MainActivity.this, menu_zona_sur.class);
                startActivity(zonaSur);
                if (bienvenido.isPlaying()){
                    bienvenido.release();
                }
                finish();
                break;
            case R.id.salir_menu_principal:
                termina++;
                consulta c = new consulta();
                c.execute();
                if (bienvenido.isPlaying()){
                    bienvenido.release();
                }
                finish();
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {

    }


    public class consulta extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            if (empieza == 1) {

                System.out.println("entre CONCHETUMARE");

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                    Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                    String query3 = "select id from sf_guard_user where nombres='" + seleccion_alumno.nombre_alumno + "' and apellido_paterno= '" + seleccion_alumno.apellido_pa_alumno + "' and apellido_materno= '" + seleccion_alumno.apellido_ma_alumno + "'";
                    Statement stm3 = c.prepareStatement(query3);
                    ResultSet rs3 = stm3.executeQuery(query3);


                    while (rs3.next()) {
                        id_pertenece = rs3.getInt("id");
                        System.out.println("id_pertenece_alumno==" + id_pertenece);
                    }

                    String query4 = "select id from PERTENECE_reim where sf_guard_user_id= '" + id_pertenece + "' ";
                    Statement stm4 = c.prepareStatement(query4);
                    ResultSet rs4 = stm4.executeQuery(query4);


                    while (rs4.next()) {
                        id_pertenece_tabla = rs4.getInt("id");

                    }
                    System.out.println("id_pertenece_tabla==" + id_pertenece_tabla);

                    PreparedStatement insert = c.prepareStatement("INSERT INTO ASIGNA_REALIZAR_SESION (REIM_id_reim, PERTENECE_id, datetime_inicio_sesion, datetime_termino_sesion) VALUES (509,?,?,'')");

                    insert.setInt(1, id_pertenece_tabla);
                    insert.setTimestamp(2, Timestamp.valueOf(s.fecha_inicio));
                    insert.execute();
                    insert.close();


                    String query2 = "select id_sesion FROM ASIGNA_REALIZAR_SESION ORDER BY id_sesion DESC LIMIT 1";
                    Statement stm2 = c.prepareStatement(query2);
                    ResultSet rs2 = stm2.executeQuery(query2);

                    if (rs2.next()) {
                        id_sesion = rs2.getInt("id_sesion");
                        System.out.println(id_sesion);
                    }

                    c.close();//cierra conexion
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();

                }
            }

            if (termina == 6) {

                System.out.println("ENTRE?");
                fecha_termino = f.fecchai;
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                    Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                    PreparedStatement primera = c.prepareStatement("UPDATE ASIGNA_REALIZAR_SESION SET datetime_termino_sesion = '" + fecha_termino + "' WHERE id_sesion ='" + id_sesion + "' ");
                    primera.execute();
                    primera.close();
                    c.close();
                    System.exit(0);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }
    }

}
