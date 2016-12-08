package com.example.joliv.anchi13;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login extends AppCompatActivity {
    Button entrar, salir;
    TextView user, contraseña;
    static String usuario, pass, salt, passbd, passEncrypt, que;
    static int id_usuario;

    HashText h = new HashText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        user = (TextView) findViewById(R.id.texto_user);
        contraseña = (TextView) findViewById(R.id.texto_pass);
        entrar = (Button) findViewById(R.id.ingresar_login);
        salir = (Button) findViewById(R.id.volvervlogin);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = user.getText().toString();
                pass = contraseña.getText().toString();
                consulta query = new consulta();
                query.execute();

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        protected Void doInBackground(Void... params) {

            try {


                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                Connection c = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");


                String query = "select salt, password from sf_guard_user where username= '" + usuario + "'";
                Statement stm = c.prepareStatement(query);
                ResultSet rs = stm.executeQuery(query);
                if (rs.next()) {

                    salt = rs.getString("salt");
                    passbd = rs.getString("password");


                }

                String query2 = "select id from sf_guard_user where username= '" + usuario + "' ";
                Statement stm2 = c.prepareStatement(query2);
                ResultSet rs2 = stm2.executeQuery(query2);

                if (rs2.next()) {
                    id_usuario = rs2.getInt("id");
                }

                passEncrypt = salt + pass;
                System.out.println(passEncrypt);


                que = h.sha1(passEncrypt);

                System.out.println("encriptada= " + que);

                String query3 = "select * from sf_guard_user where username= '" + usuario + "' and password = '" + que + "'  ";
                Statement stm3 = c.prepareStatement(query3);
                ResultSet rs3 = stm3.executeQuery(query3);

                if (rs3.next()) {

                    Intent i = new Intent(login.this, seleccion_curso.class);
                    startActivity(i);

                }


                c.close();//cierra conexion

            } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();

            }

            return null;
        }
    }
}