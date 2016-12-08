package com.example.joliv.anchi13;


import android.content.Intent;
import android.media.MediaPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

public class entrega_liston_plata extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer entregaliston;
    ImageButton flecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_liston_plata);


        flecha = (ImageButton) findViewById(R.id.flechaPlata);
        flecha.setOnClickListener(this);


        entregaliston = MediaPlayer.create(this, R.raw.sonidomedalla);
        entregaliston.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.flechaPlata:

                Intent menu = new Intent(entrega_liston_plata.this, menu_zona_centro.class);
                startActivity(menu);
                break;

        }
    }
}
