package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay, btnRanking, btnYourRecords, btnClose;

    private com.google.android.material.floatingactionbutton.FloatingActionButton btnShareTheApp, btnInfo, btnWebsite;

    private MediaPlayer mysong, buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMain);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        mysong = MediaPlayer.create(this, R.raw.main_music);
        mysong.setLooping(true);
        mysong.start();
        buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnYourRecords = (Button) findViewById(R.id.btnYourRecords);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnShareTheApp = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.btnShareTheApp);
        btnInfo = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.btnInfo);
        btnWebsite = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.btnWebsite);
        btnPlay.setOnClickListener(this::onClick);
        btnRanking.setOnClickListener(this::onClick);
        btnYourRecords.setOnClickListener(this::onClick);
        btnClose.setOnClickListener(this::onClick);
        btnShareTheApp.setOnClickListener(this::onClick);
        btnInfo.setOnClickListener(this::onClick);
        btnWebsite.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnPlay) {
            buttonSound.start();
            intent = new Intent(this, GameMenuActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnRanking) {
            buttonSound.start();
            intent = new Intent(this, RankingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnYourRecords) {
            buttonSound.start();
            intent = new Intent(this, RecordsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnClose) {
            buttonSound.start();
            finish();
            System.exit(0);
        } else if (view.getId() == R.id.btnShareTheApp){
            buttonSound.start();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Download Match Pairs Memory Game via Github" +
                    "\nhttps://github.com/Flucus/");
            startActivity(Intent.createChooser(sendIntent,"Share via"));
        } else if (view.getId() == R.id.btnInfo){
            buttonSound.start();
            intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnWebsite){
            buttonSound.start();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://github.com/Flucus/"));
            startActivity(browserIntent);
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
            mysong = MediaPlayer.create(this, R.raw.main_music);
            mysong.setLooping(true);
            mysong.start();
        }
    }
}