package com.slavikart.hw_09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private TextView tvHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("BrainPrefs", MODE_PRIVATE);
        tvHighScore = findViewById(R.id.tvHighScore);

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> startTraining());

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        updateHighScore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHighScore();
    }

    private void updateHighScore() {
        int highScore = prefs.getInt("highScore", 0);
        tvHighScore.setText("High Score: " + highScore);
    }

    private void startTraining() {
        SharedPreferences prefs = getSharedPreferences("BrainPrefs", MODE_PRIVATE);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", prefs.getInt("difficulty", 0));
        intent.putExtra("math", prefs.getBoolean("math", true));
        intent.putExtra("puzzles", prefs.getBoolean("puzzles", false));
        startActivity(intent);
    }

    private void saveToInternalStorage(int score) {
        try {
            FileOutputStream fos = openFileOutput("training_log.txt", MODE_APPEND);
            String entry = new Date() + " | Score: " + score + "\n";
            fos.write(entry.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToExternalStorage(int score) {
        if(getExternalFilesDir(null) != null) {
            File file = new File(getExternalFilesDir(null), "external_log.txt");
            try(FileOutputStream fos = new FileOutputStream(file, true)) {
                String entry = new Date() + " | External Score: " + score + "\n";
                fos.write(entry.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
