package com.example.joliv.anchi13;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class menu_zona_sur extends AppCompatActivity implements View.OnClickListener {

    ImageButton animal1;
    ImageButton animal2;
    ImageButton animal3;
    ImageButton salir;
    MediaPlayer selecciona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_zona_sur);

        animal1 = (ImageButton) findViewById(R.id.animal1_zonasur);
        animal2 = (ImageButton) findViewById(R.id.animal2_zonasur);
        animal3 = (ImageButton) findViewById(R.id.animal3_zonasur);
        salir = (ImageButton) findViewById(R.id.salir_menuzonasur);
        selecciona = MediaPlayer.create(menu_zona_sur.this, R.raw.selecciona_animal);

        animal1.setOnClickListener(this);
        animal2.setOnClickListener(this);
        animal3.setOnClickListener(this);
        salir.setOnClickListener(this);

        selecciona.start();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.animal1_zonasur:
                Intent intent = new Intent(menu_zona_sur.this, actividad_animal1_zonasur.class);
                startActivity(intent);
                if (selecciona.isPlaying()){
                    selecciona.release();
                }
                break;

            case R.id.animal2_zonasur:
                Intent intent2 = new Intent(menu_zona_sur.this, actividad_animal2_zonasur.class);
                startActivity(intent2);
                if (selecciona.isPlaying()){
                    selecciona.release();
                }
                break;
            case R.id.animal3_zonasur:
                Intent intent3 = new Intent(menu_zona_sur.this, actividad_animal3_zonasur.class);
                startActivity(intent3);
                if (selecciona.isPlaying()){
                    selecciona.release();
                }
                break;
            case R.id.salir_menuzonasur:
                Intent intent4 = new Intent(menu_zona_sur.this, MainActivity.class);
                startActivity(intent4);
                if (selecciona.isPlaying()){
                    selecciona.release();
                }
                onStop();
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
}
