package com.example.joliv.anchi13;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by joliv on 02-12-2016.
 */
public class login_nuevo extends Activity {

    EditText edtuserid, edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    static int id_usuario;
    static String salt, passbd, passEncrypt, pass_nueva;
    HashText h = new HashText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.el_nuevo_login);

        edtuserid = (EditText) findViewById(R.id.edtuserid);
        edtpass = (EditText) findViewById(R.id.edtpass);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

            }
        });


    }

    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;


        String user = edtuserid.getText().toString();
        String password = edtpass.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(login_nuevo.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Intent i = new Intent(login_nuevo.this, seleccion_curso.class);
                startActivity(i);
                finish();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (user.trim().equals("") || password.trim().equals(""))
                z = "Por favor ingrese un Usuario y una Contraseña";
            else {
                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    String url = "jdbc:mysql://mysql.ulearnet.com:3306/ulearnet_des";
                    Connection con = DriverManager.getConnection(url, "ulearnet_des", "ulearnet_des@");

                    if (con == null) {
                        z = "Error en la conexion con el servidor de Ulearnet";
                    } else {

                        String query = "select id, salt, password from sf_guard_user where username= '" + user + "'";

                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);


                        if (rs.next()) {

                            id_usuario = rs.getInt("id");
                            salt = rs.getString("salt");
                            passbd = rs.getString("password");
                        }

                        passEncrypt = salt + password;
                        System.out.println(passEncrypt);
                        pass_nueva = h.sha1(passEncrypt);

                        String query2 = "select * from sf_guard_user where username= '" + user + "' and password = '" + pass_nueva +"' ";

                        Statement stm2 = con.prepareStatement(query2);
                        ResultSet rs2 = stm2.executeQuery(query2);

                        if (rs2.next()) {

                            z = "Login Exitoso!";
                            isSuccess = true;

                        } else {
                            z = "Usuario o Contraseña incorrectos";
                            isSuccess = false;
                        }

                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }


}
