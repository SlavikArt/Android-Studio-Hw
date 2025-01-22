package com.slavikart.hw_02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN = "MainActivity";

    private long onCreateTime;
    private long onStartTime;
    private long onResumeTime;
    private long onPauseTime;
    private long onStopTime;
    private long onDestroyTime;
    private long buttonClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        onCreateTime = System.currentTimeMillis();
        Log.d(MAIN, "onCreate called at: " + onCreateTime);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Button button_click = findViewById(R.id.button_click);
        button_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickTime = System.currentTimeMillis();
                Log.d(MAIN, "Button 1 clicked at: " + buttonClickTime);
            }
        });

        Button userGuessButton = findViewById(R.id.button_user_guess);
        userGuessButton.setOnClickListener(v -> {
            buttonClickTime = System.currentTimeMillis();
            Log.d(MAIN, "User Guess button clicked at: " + buttonClickTime);

            Intent intent = new Intent(MainActivity.this, UserGuessActivity.class);
            startActivity(intent);
        });

        Button computerGuessButton = findViewById(R.id.button_computer_guess);
        computerGuessButton.setOnClickListener(v -> {
            buttonClickTime = System.currentTimeMillis();
            Log.d(MAIN, "Computer Guess button clicked at: " + buttonClickTime);

            Intent intent = new Intent(MainActivity.this, ComputerGuessActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartTime = System.currentTimeMillis();
        Log.d(MAIN, "onStart called at: " + onStartTime
                + " (Delay from onCreate: " + (onStartTime - onCreateTime) + " ms)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeTime = System.currentTimeMillis();
        Log.d(MAIN, "onResume called at: " + onResumeTime
                + " (Delay from onStart: " + (onResumeTime - onStartTime) + " ms)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseTime = System.currentTimeMillis();
        Log.d(MAIN, "onPause called at: " + onPauseTime
                + " (Delay from onResume: " + (onPauseTime - onResumeTime) + " ms)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStopTime = System.currentTimeMillis();
        Log.d(MAIN, "onStop called at: " + onStopTime
                + " (Delay from onPause: " + (onStopTime - onPauseTime) + " ms)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyTime = System.currentTimeMillis();
        Log.d(MAIN, "onDestroy called at: "
                + onDestroyTime + " (Delay from onStop: " + (onDestroyTime - onStopTime) + " ms)");
    }
}