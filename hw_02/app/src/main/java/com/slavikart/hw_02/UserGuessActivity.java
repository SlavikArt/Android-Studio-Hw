package com.slavikart.hw_02;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class UserGuessActivity extends AppCompatActivity {

    private static final String USER_GUESS = "UserGuessActivity";

    private int numberToGuess;
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guess);

        numberToGuess = new Random().nextInt(100) + 1;
        attempts = 0;
        Log.d(USER_GUESS, "Number to guess: " + numberToGuess);

        TextView infoText = findViewById(R.id.info_text);
        EditText inputNumber = findViewById(R.id.input_number);
        Button guessButton = findViewById(R.id.guess_button);

        guessButton.setOnClickListener(v -> {
            String userInput = inputNumber.getText().toString();
            if (!userInput.isEmpty()) {
                int guessedNumber = Integer.parseInt(userInput);
                attempts++;
                Log.d(USER_GUESS, "User guessed: " + guessedNumber);

                if (guessedNumber == numberToGuess) {
                    infoText.setText("Correct! You guessed the number in " + attempts + " attempts.");
                } else if (guessedNumber < numberToGuess) {
                    infoText.setText("<<< Too low!");
                } else {
                    infoText.setText(">>> Too high!");
                }
            } else {
                infoText.setText("Please enter a number.");
            }
        });
    }
}