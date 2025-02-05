package com.slavikart.hw_09;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("BrainPrefs", MODE_PRIVATE);
        loadSettings();

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        Spinner difficulty = findViewById(R.id.spinnerDifficulty);
        CheckBox cbMath = findViewById(R.id.cbMath);
        CheckBox cbPuzzles = findViewById(R.id.cbPuzzles);

        difficulty.setSelection(prefs.getInt("difficulty", 0));
        cbMath.setChecked(prefs.getBoolean("math", true));
        cbPuzzles.setChecked(prefs.getBoolean("puzzles", false));
    }

    private void saveSettings() {
        Spinner difficulty = findViewById(R.id.spinnerDifficulty);
        CheckBox cbMath = findViewById(R.id.cbMath);
        CheckBox cbPuzzles = findViewById(R.id.cbPuzzles);

        prefs.edit()
                .putInt("difficulty", difficulty.getSelectedItemPosition())
                .putBoolean("math", cbMath.isChecked())
                .putBoolean("puzzles", cbPuzzles.isChecked())
                .apply();
        finish();
    }
}
