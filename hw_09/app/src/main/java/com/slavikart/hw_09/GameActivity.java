package com.slavikart.hw_09;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private List<Object> tasks = new ArrayList<>();
    private int currentTaskIndex = 0;
    private int score = 0;
    private int difficulty;

    private TextView tvQuestion, tvScore;
    private ImageView ivPictogram;
    private EditText etAnswer;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeViews();
        setupGameParameters();
        prepareTasks();
        showNextTask();
    }

    private void initializeViews() {
        tvQuestion = findViewById(R.id.tvQuestion);
        ivPictogram = findViewById(R.id.ivPictogram);
        etAnswer = findViewById(R.id.etAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvScore = findViewById(R.id.tvScore);
    }

    private void setupGameParameters() {
        difficulty = getIntent().getIntExtra("difficulty", 0);
        boolean mathEnabled = getIntent().getBooleanExtra("math", true);
        boolean puzzlesEnabled = getIntent().getBooleanExtra("puzzles", false);
    }

    private void prepareTasks() {
        boolean math = getIntent().getBooleanExtra("math", true);
        boolean puzzles = getIntent().getBooleanExtra("puzzles", false);

        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            if (math && puzzles)
                tasks.add(rand.nextBoolean() ? new ArithmeticTask(difficulty)
                        : getRandomPictogram(difficulty));
            else if (math)
                tasks.add(new ArithmeticTask(difficulty));
            else if (puzzles)
                tasks.add(getRandomPictogram(difficulty));
        }
        Collections.shuffle(tasks);
    }

    private PictogramTask getRandomPictogram(int difficulty) {
        List<PictogramTask> easyPictograms = Arrays.asList(
                new PictogramTask(R.drawable.fish, "fish", 0),
                new PictogramTask(R.drawable.coffee, "coffee", 0),
                new PictogramTask(R.drawable.meat, "meat", 0)
        );

        List<PictogramTask> mediumPictograms = Arrays.asList(
                new PictogramTask(R.drawable.pizza, "pizza", 1),
                new PictogramTask(R.drawable.milk, "milk", 1),
                new PictogramTask(R.drawable.candy, "candy", 1)
        );

        List<PictogramTask> hardPictograms = Arrays.asList(
                new PictogramTask(R.drawable.honey, "honey", 2),
                new PictogramTask(R.drawable.oven, "oven", 2),
                new PictogramTask(R.drawable.sushi, "sushi", 2)
        );

        Random rand = new Random();
        List<PictogramTask> selectedPictograms = new ArrayList<>();

        switch (difficulty) {
            case 0:
                // Easy
                selectedPictograms.addAll(easyPictograms);
                break;
            case 1:
                // Easy + Medium
                selectedPictograms.addAll(easyPictograms);
                selectedPictograms.addAll(mediumPictograms);
                break;
            case 2:
                // Easy + Medium + Hard
                selectedPictograms.addAll(easyPictograms);
                selectedPictograms.addAll(mediumPictograms);
                selectedPictograms.addAll(hardPictograms);
                break;
            default:
                selectedPictograms.addAll(easyPictograms);
                break;
        }
        return selectedPictograms.get(rand.nextInt(selectedPictograms.size()));
    }

    private void showNextTask() {
        if (currentTaskIndex >= tasks.size()) {
            endGame();
            return;
        }

        Object task = tasks.get(currentTaskIndex);
        setupTaskUI(task);
        btnSubmit.setOnClickListener(v -> checkAnswer(task));
    }

    private void setupTaskUI(Object task) {
        ivPictogram.setVisibility(View.GONE);
        tvQuestion.setVisibility(View.VISIBLE);

        if (task instanceof ArithmeticTask) {
            ArithmeticTask mathTask = (ArithmeticTask) task;
            tvQuestion.setText(mathTask.getQuestion());
        } else {
            PictogramTask pictoTask = (PictogramTask) task;
            ivPictogram.setImageResource(pictoTask.getImageId());
            ivPictogram.setVisibility(View.VISIBLE);
            tvQuestion.setVisibility(View.GONE);
        }
    }

    private void checkAnswer(Object task) {
        boolean isCorrect = false;
        String userAnswer = etAnswer.getText().toString().trim();

        if (task instanceof ArithmeticTask) {
            try {
                int answer = Integer.parseInt(userAnswer);
                isCorrect = ((ArithmeticTask) task).checkAnswer(answer);
            } catch (NumberFormatException e) {
                isCorrect = false;
            }
        } else if (task instanceof PictogramTask) {
            isCorrect = ((PictogramTask) task).checkAnswer(userAnswer);
        }
        updateScoreAndFeedback(isCorrect, task);
    }

    private void updateScoreAndFeedback(boolean isCorrect, Object task) {
        if (isCorrect) {
            score += (difficulty + 1) * 10;
            tvScore.setText("Рахунок: " + score);
            showFeedback("Правильно! ✅", "", true);
        } else {
            String correctAnswer = "";
            if (task instanceof ArithmeticTask)
                correctAnswer = "\nПравильна відповідь: " + ((ArithmeticTask) task).getAnswer();
            else if (task instanceof PictogramTask)
                correctAnswer = "\nПравильна відповідь: " + ((PictogramTask) task).getAnswer();
            showFeedback("Невірно! ❌", correctAnswer, true);
        }
    }

    private void showFeedback(String result, String additionalInfo, boolean delay) {
        etAnswer.setEnabled(false);
        btnSubmit.setEnabled(false);
        tvQuestion.setVisibility(View.VISIBLE);
        tvQuestion.setText(result + additionalInfo);

        // Delay 1.5s
        if (delay)
            new Handler().postDelayed(this::proceedToNextTask, 1500);
    }

    private void proceedToNextTask() {
        currentTaskIndex++;
        etAnswer.setText("");
        etAnswer.setEnabled(true);
        btnSubmit.setEnabled(true);
        showNextTask();
    }

    private void endGame() {
        finish();
    }
}
