package com.example.joliv.anchi13;


import android.content.Intent;
import android.media.MediaPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

public class entrega_liston_prueba extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer entregaliston;
    ImageButton flecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_liston_prueba);


        flecha = (ImageButton) findViewById(R.id.flecha);
        flecha.setOnClickListener(this);


        entregaliston = MediaPlayer.create(this, R.raw.sonidomedalla);
        entregaliston.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.flecha:

                Intent menu = new Intent(entrega_liston_prueba.this, menu_zona_norte.class);
                startActivity(menu);
                break;

        }
    }
}
