package com.slavikart.hw_02;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ComputerGuessActivity extends AppCompatActivity {

    private static final String COMPUTER_GUESS = "ComputerGuessActivity";

    private int low;
    private int high;
    private int computerGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_guess);

        low = 1;
        high = 100;
//        computerGuess = new Random().nextInt(high - low + 1) + low;
        computerGuess = 50;
        Log.d(COMPUTER_GUESS, "Computer's initial guess: " + computerGuess);

        TextView infoText = findViewById(R.id.info_text);
        Button lowerButton = findViewById(R.id.lower_button);
        Button higherButton = findViewById(R.id.higher_button);
        Button correctButton = findViewById(R.id.correct_button);

        infoText.setText("Is your number " + computerGuess + "?");

        lowerButton.setOnClickListener(v -> {
            high = computerGuess - 1;
            computerGuess = new Random().nextInt(high - low + 1) + low;
            Log.d(COMPUTER_GUESS, "Computer guesses lower: " + computerGuess);
            infoText.setText("Is your number " + computerGuess + "?");
        });

        higherButton.setOnClickListener(v -> {
            low = computerGuess + 1;
            computerGuess = new Random().nextInt(high - low + 1) + low;
            Log.d(COMPUTER_GUESS, "Computer guesses higher: " + computerGuess);
            infoText.setText("Is your number " + computerGuess + "?");
        });

        correctButton.setOnClickListener(v -> {
            Log.d(COMPUTER_GUESS, "Computer guessed correctly: " + computerGuess);
            infoText.setText("Yay! I guessed your number: " + computerGuess);
        });
    }
}
