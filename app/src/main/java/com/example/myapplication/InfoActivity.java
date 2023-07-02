package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    private MediaPlayer mysong, buttonSound;

    private com.google.android.material.floatingactionbutton.FloatingActionButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMain);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        mysong = MediaPlayer.create(this, R.raw.info_music);
        mysong.setLooping(true);
        mysong.start();
        buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        btnBack = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnBack) {
            buttonSound.start();
            finish();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mysong != null) {
            mysong.stop(); // Stop the music
            mysong.release(); // Release the MediaPlayer resources
            mysong = null;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (mysong == null) {
            mysong = MediaPlayer.create(this, R.raw.info_music);
            mysong.setLooping(true);
            mysong.start();
        }
    }
}
