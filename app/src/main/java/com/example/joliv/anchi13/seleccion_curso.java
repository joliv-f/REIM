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


public class seleccion_curso extends AppCompatActivity {
    Button siguiente;

    ArrayList<String> coleccion = new ArrayList<>();
    ArrayAdapter<String> adaptador;
    ListView lista;

    static String elemento_lista_curso, nombre_curso;

    login_nuevo login = new login_nuevo();
    ProgressBar pbbar_curso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_curso);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Weas para lista de cursos
        lista = (ListView) findViewById(R.id.lista1);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coleccion);
        lista.setAdapter(adaptador);

        pbbar_curso = (ProgressBar) findViewById(R.id.progressBar_curso);
        pbbar_curso.setVisibility(View.GONE);


        //Cargamos cursos correspondientes al usuario login.
        consulta query = new consulta();
        query.execute();

        siguiente = (Button) findViewById(R.id.siguiente);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             elemento_lista_curso = String.valueOf(parent.getItemAtPosition(position));

                                             Toast.makeText(seleccion_curso.this, "Seleccionó el curso: " + elemento_lista_curso, Toast.LENGTH_LONG).show();
                                         }
                                     }
        );
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elemento_lista_curso != null) {
                    Intent intent = new Intent(seleccion_curso.this, seleccion_alumno.class);
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
            pbbar_curso.setVisibility(View.VISIBLE);
        }


        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            pbbar_curso.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                String query = "select nombre FROM curso c , PERTENECE_reim p , sf_guard_user u where u.id='" + login.id_usuario + "' and p.sf_guard_user_id='" + login.id_usuario + "' and p.CURSO_id_curso=c.id";

                Statement stm = c.prepareStatement(query);
                ResultSet rs = stm.executeQuery(query);

                while (rs.next()) {
                    nombre_curso = rs.getString("nombre");
                    coleccion.add(nombre_curso);
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
