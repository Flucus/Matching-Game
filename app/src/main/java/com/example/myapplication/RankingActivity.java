package com.example.myapplication;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private ListView listView;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnBack;

    private MediaPlayer mysong, buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMain);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        mysong = MediaPlayer.create(this, R.raw.ranking_music);
        mysong.setLooping(true);
        mysong.start();
        buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        btnBack = (com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this::onClick);
        listView = findViewById(R.id.listView);
        new fetchJSON(listView).execute();
    }


    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            buttonSound.start();
            finish();
        }
    }

    private class fetchJSON extends AsyncTask<String, Void, String> {
        private ListView listView;

        public fetchJSON(ListView listView) {
            this.listView = listView;
        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            try {
                URL urlString = new URL("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run");
                HttpURLConnection connection = (HttpURLConnection) urlString.openConnection();
                connection.setReadTimeout(100000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();

                InputStream stream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    json += current;
                }
                stream.close();
            } catch (Exception e) {
                e.getMessage();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                List<JSONObject> jsonObjects = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObjects.add(jsonArray.getJSONObject(i));
                }
                Collections.sort(jsonObjects, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject jsonObject1, JSONObject jsonObject2) {
                        try {
                            int moves1 = jsonObject1.getInt("Moves");
                            int moves2 = jsonObject2.getInt("Moves");
                            return Integer.compare(moves1, moves2);
                        } catch (Exception e) {
                            e.getMessage();
                            return 0;
                        }
                    }
                });
                List<String> rankings = new ArrayList<>();
                for (int i = 0; i < jsonObjects.size(); i++) {
                    try {
                        String name = jsonObjects.get(i).getString("Name");
                        int moves = jsonObjects.get(i).getInt("Moves");
                        String ranking = (i + 1) + ") " + name + " completed the game in " + moves + " moves!";
                        rankings.add(ranking);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(RankingActivity.this, android.R.layout.simple_list_item_1, rankings);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                e.getMessage();
            }
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
            mysong = MediaPlayer.create(this, R.raw.ranking_music);
            mysong.setLooping(true);
            mysong.start();
        }
    }
}
