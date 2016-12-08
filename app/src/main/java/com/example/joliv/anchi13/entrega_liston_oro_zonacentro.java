package com.example.joliv.anchi13;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class entrega_liston_oro_zonacentro extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer entregaliston;
    ImageButton flecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_liston_oro);


        flecha = (ImageButton) findViewById(R.id.flechaOro);
        flecha.setOnClickListener(this);


        entregaliston = MediaPlayer.create(this, R.raw.sonidomedalla);
        entregaliston.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.flechaOro:

                Intent menu = new Intent(entrega_liston_oro_zonacentro.this, menu_zona_centro.class);
                startActivity(menu);
                onStop();
                break;

        }
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
}
