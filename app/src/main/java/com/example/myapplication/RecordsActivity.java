package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class RecordsActivity extends AppCompatActivity {

    private com.google.android.material.floatingactionbutton.FloatingActionButton btnShareRecord, btnUploadRecord, btnDownloadRecord, btnDeleteRecord;
    private LinearLayout llMain;

    MediaPlayer mysong, buttonSound;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llMain);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        mysong = MediaPlayer.create(this, R.raw.records_music);
        mysong.setLooping(true);
        mysong.start();
        buttonSound = MediaPlayer.create(this, R.raw.button_sound);

        btnShareRecord = findViewById(R.id.btnShareRecord);
        btnDownloadRecord = findViewById(R.id.btnDownloadRecord);
        btnDeleteRecord = findViewById(R.id.btnDeleteRecord);
        btnUploadRecord = findViewById(R.id.btnUploadRecord);
        llMain = findViewById(R.id.llMain);
        listView = findViewById(R.id.lvRecords);

        List<String> records = loadRecords();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);


        btnDownloadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                downloadDatabase();
            }
        });

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);
                builder.setTitle("Delete Record")
                        .setMessage("Are you sure you want to delete all record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buttonSound.start();
                                deleteDatabase();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buttonSound.start();
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnUploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                openFileExplorer();
                loadRecords();
            }
        });

        //ChatGPT Generated Code
        btnShareRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                takeScreenshotAndShare();
            }

        });

    }

    private List<String> loadRecords() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("data/data/com.example.myapplication/MemberDB.db", null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "CREATE TABLE IF NOT EXISTS GamesLog (gameID INTEGER PRIMARY KEY AUTOINCREMENT, playDate TEXT, playTime TEXT, moves INTEGER, duration TEXT, LEVEL TEXT)";
        db.execSQL(sql);
        Cursor cursor = db.rawQuery("SELECT playDate, playTime, moves, duration, LEVEL FROM GamesLog ORDER BY playDate DESC, playTime DESC", null, null);
        List<String> records = new ArrayList<>();
        while (cursor.moveToNext()) {
            String playDate = cursor.getString(cursor.getColumnIndexOrThrow("playDate"));
            String playTime = cursor.getString(cursor.getColumnIndexOrThrow("playTime"));
            int moves = cursor.getInt(cursor.getColumnIndexOrThrow("moves"));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            String level = cursor.getString(cursor.getColumnIndexOrThrow("LEVEL"));
            String dataStr = String.format("%s %s - (%s) %d moves in %s s!", playDate, playTime, level, moves, duration);
            records.add(dataStr);
        }
        cursor.close();
        db.close();
        return records;
    }

    private void takeScreenshotAndShare() {
        llMain.setDrawingCacheEnabled(true);
        Bitmap screenshotBitmap = Bitmap.createBitmap(llMain.getDrawingCache());
        llMain.setDrawingCacheEnabled(false);
        File screenshotFile = saveBitmapToFile(screenshotBitmap);
        shareScreenshot(screenshotFile);
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        try {
            File cachePath = new File(getCacheDir(), "screenshots");
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/screenshot.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            return new File(cachePath + "/screenshot.png");
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    private void shareScreenshot(File screenshotFile) {
        if (screenshotFile != null && screenshotFile.exists()) {
            Uri screenshotUri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".fileprovider",
                    screenshotFile
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share Game Records"));
        }
    }

    private void downloadDatabase() {
        try {
            String sourcePath = "data/data/com.example.myapplication/MemberDB.db";

            File sourceFile = new File(sourcePath);

            String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

            File destinationFile = new File(downloadsPath, "MemberDB.db");

            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fos.flush();
            fos.close();
            fis.close();

            Toast.makeText(this, "Database downloaded to the Download folder", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void deleteDatabase() {
        String databasePath = "/data/data/com.example.myapplication/MemberDB.db";
        File databaseFile = new File(databasePath);
        if (databaseFile.exists()) {
            if (databaseFile.delete()) {
                Toast.makeText(this, "Database deleted successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to delete database", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Database does not exist", Toast.LENGTH_LONG).show();
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
            mysong = MediaPlayer.create(this, R.raw.records_music);
            mysong.setLooping(true);
            mysong.start();
        }
    }

    private void openFileExplorer() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    try {
                        String destinationPath = "data/data/com.example.myapplication/MemberDB.db";
                        File destinationFile = new File(destinationPath);

                        InputStream inputStream = getContentResolver().openInputStream(fileUri);
                        OutputStream outputStream = new FileOutputStream(destinationFile);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }

                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();

                        Toast.makeText(this, "File uploaded successfully", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to upload file", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
