package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameActivityHard extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMoves;
    private int moveCount;

    private Button prevButton, btnContinue, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17;

    private Button btnBackToMenu;

    private com.google.android.material.floatingactionbutton.FloatingActionButton btnBack;
    private int pairsFound;
    private List<String> items;

    private double startTime;

    private CountDownTimer timer;

    private MediaPlayer mysong, buttonSound, success_move, move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_hard);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMain);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        initializeGame();
        buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        mysong = MediaPlayer.create(this, R.raw.game_music_hard);
        mysong.start();
        mysong.setLooping(true);
        mysong.setVolume(0.5f, 0.5f);
        success_move = MediaPlayer.create(this, R.raw.success_move);
        move = MediaPlayer.create(this, R.raw.move);
    }

    private void initializeGame() {
        tvMoves = findViewById(R.id.tvMoves);
        tvMoves.setText("Moves: " + moveCount);
        moveCount = 0;
        pairsFound = 0;
        items = new ArrayList<>(Arrays.asList("\uD83D\uDFE5", "\uD83D\uDFE5", "\uD83D\uDFE7", "\uD83D\uDFE7", "\uD83D\uDFE8", "\uD83D\uDFE8", "\uD83D\uDFE9", "\uD83D\uDFE9", "\uD83D\uDFE6", "\uD83D\uDFE6", "\uD83D\uDFEA", "\uD83D\uDFEA", "\uD83D\uDFEB", "\uD83D\uDFEB", "⬛", "⬛", "⬜", "⬜"));
        Collections.shuffle(items);
        btn0 = findViewById(R.id.btn0);
        btn0.setText("");
        btn0.setVisibility(View.VISIBLE);
        btn0.setOnClickListener(this);
        btn1 = findViewById(R.id.btn1);
        btn1.setText("");
        btn1.setVisibility(View.VISIBLE);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn2);
        btn2.setText("");
        btn2.setVisibility(View.VISIBLE);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn3);
        btn3.setText("");
        btn3.setVisibility(View.VISIBLE);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn4);
        btn4.setText("");
        btn4.setVisibility(View.VISIBLE);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn5);
        btn5.setText("");
        btn5.setVisibility(View.VISIBLE);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn6);
        btn6.setText("");
        btn6.setVisibility(View.VISIBLE);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn7);
        btn7.setText("");
        btn7.setVisibility(View.VISIBLE);
        btn7.setOnClickListener(this);
        btn8 = findViewById(R.id.btn8);
        btn8.setText("");
        btn8.setVisibility(View.VISIBLE);
        btn8.setOnClickListener(this);
        btn9 = findViewById(R.id.btn9);
        btn9.setText("");
        btn9.setVisibility(View.VISIBLE);
        btn9.setOnClickListener(this);
        btn10 = findViewById(R.id.btn10);
        btn10.setText("");
        btn10.setVisibility(View.VISIBLE);
        btn10.setOnClickListener(this);
        btn11 = findViewById(R.id.btn11);
        btn11.setText("");
        btn11.setVisibility(View.VISIBLE);
        btn11.setOnClickListener(this);
        btn12 = findViewById(R.id.btn12);
        btn12.setText("");
        btn12.setVisibility(View.VISIBLE);
        btn12.setOnClickListener(this);
        btn13 = findViewById(R.id.btn13);
        btn13.setText("");
        btn13.setVisibility(View.VISIBLE);
        btn13.setOnClickListener(this);
        btn14 = findViewById(R.id.btn14);
        btn14.setText("");
        btn14.setVisibility(View.VISIBLE);
        btn14.setOnClickListener(this);
        btn15 = findViewById(R.id.btn15);
        btn15.setText("");
        btn15.setVisibility(View.VISIBLE);
        btn15.setOnClickListener(this);
        btn16 = findViewById(R.id.btn16);
        btn16.setText("");
        btn16.setVisibility(View.VISIBLE);
        btn16.setOnClickListener(this);
        btn17 = findViewById(R.id.btn17);
        btn17.setText("");
        btn17.setVisibility(View.VISIBLE);
        btn17.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnBack.setVisibility(View.VISIBLE);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        btnContinue.setVisibility(View.INVISIBLE);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(this);
        btnBackToMenu.setVisibility(View.INVISIBLE);
        startTime = System.currentTimeMillis();
        startTimer(45000);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnContinue) {
            buttonSound.start();
            mysong.start();
            initializeGame();
        } else if (v.getId() == R.id.btnBackToMenu || v.getId() == R.id.btnBack) {
            mysong.stop();
            buttonSound.start();
            initializeGame();
            finish();
        } else {
            buttonSound.start();
            Button button = (Button) v;
            String item = items.get(Integer.parseInt(button.getTag().toString()));

            if (button.getVisibility() == View.INVISIBLE || button == prevButton) {
                return;
            }

            button.setText(item);

            if (prevButton == null) {
                prevButton = button;
            } else {
                move.start();
                moveCount++;
                if (items.get(Integer.parseInt(prevButton.getTag().toString())).equals(item)) {
                    success_move.start();
                    button.setVisibility(View.INVISIBLE);
                    prevButton.setVisibility(View.INVISIBLE);
                    pairsFound++;
                    if (pairsFound == 9) {
                        btnBack.setVisibility(View.INVISIBLE);
                        timer.cancel();
                        double endTime = System.currentTimeMillis();
                        double durationInSeconds = (endTime - startTime) / 1000.0;
                        String formattedDuration = String.format(Locale.getDefault(), "%.2f", durationInSeconds);
                        tvMoves.setText("Finished Hard Level!\nMoves: " + moveCount + "\nTime: " + formattedDuration + "s");
                        btnContinue.setVisibility(View.VISIBLE);
                        btnBackToMenu.setVisibility(View.VISIBLE);

                        String gameDate = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault()).format(new Date());
                        String playTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        int gameMoves = moveCount;

                        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.myapplication/MemberDB.db", null, SQLiteDatabase.CREATE_IF_NECESSARY);

                        String sql = "CREATE TABLE IF NOT EXISTS GamesLog (gameID INTEGER PRIMARY KEY AUTOINCREMENT, playDate TEXT, playTime TEXT, moves INTEGER, duration TEXT, LEVEL TEXT)";
                        db.execSQL(sql);

                        ContentValues values = new ContentValues();
                        values.put("playDate", gameDate);
                        values.put("playTime", playTime);
                        values.put("moves", gameMoves);
                        values.put("duration", formattedDuration);
                        values.put("LEVEL", "Hard");
                        db.insert("GamesLog", null, values);
                        Toast toast = Toast.makeText(getApplicationContext(), "Game Record Saved, check 'YOUR RECORDS' section for more details!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    prevButton = null;
                } else {
                    button.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.setVisibility(View.VISIBLE);
                            button.setText("");
                            prevButton.setVisibility(View.VISIBLE);
                            prevButton.setText("");
                            prevButton = null;
                        }
                    }, 100);
                }
            }
        }
    }

    private void startTimer(long duration) {
        timer = new CountDownTimer(duration, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                double seconds = millisUntilFinished / 1000.0;
                String formattedSeconds = String.format(Locale.getDefault(), "%.2f", seconds);
                tvMoves.setText("Hard - Moves: " + moveCount + " Time: " + formattedSeconds + "s");
            }

            @Override
            public void onFinish() {
                tvMoves.setText("Hard Level - GAME OVER!");
                btn0.setVisibility(View.INVISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                btn6.setVisibility(View.INVISIBLE);
                btn7.setVisibility(View.INVISIBLE);
                btn8.setVisibility(View.INVISIBLE);
                btn9.setVisibility(View.INVISIBLE);
                btn10.setVisibility(View.INVISIBLE);
                btn11.setVisibility(View.INVISIBLE);
                btn12.setVisibility(View.INVISIBLE);
                btn13.setVisibility(View.INVISIBLE);
                btn14.setVisibility(View.INVISIBLE);
                btn15.setVisibility(View.INVISIBLE);
                btn16.setVisibility(View.INVISIBLE);
                btn17.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.INVISIBLE);
                btnContinue.setVisibility(View.VISIBLE);
                btnBackToMenu.setVisibility(View.VISIBLE);
            }
        }.start();
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
            mysong = MediaPlayer.create(this, R.raw.game_music_hard);
            mysong.setLooping(true);
            mysong.start();
        }
    }
}
