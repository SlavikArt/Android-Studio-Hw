package com.slavikart.hw_04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SeekBar salarySeekBar;
    private TextView salaryValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salarySeekBar = findViewById(R.id.salary);
        salaryValueText = findViewById(R.id.salaryValue);

        salaryValueText.setText(getSalaryText(salarySeekBar.getProgress()));
        salarySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                salaryValueText.setText(getSalaryText(progress));
                updateTextViewPosition(seekBar, progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button submitButton = findViewById(R.id.submitSurvey);
        submitButton.setOnClickListener(v -> validateAndCalculate());
    }

    private void validateAndCalculate() {
        EditText ageInput = findViewById(R.id.age);
        String ageStr = ageInput.getText().toString().trim();
        if (ageStr.isEmpty()) {
            ageInput.setError("Введіть вік");
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            ageInput.setError("Невірний формат віку");
            return;
        }
        boolean isAgeValid = age >= 21 && age <= 40;

        int salary = 1200 + salarySeekBar.getProgress() * 10;
        boolean isSalaryValid = salary >= 1500 && salary <= 2000;

        int points = 0;
        if (((RadioButton) findViewById(R.id.q1_option2)).isChecked()) points += 2;
        if (((RadioButton) findViewById(R.id.q2_option2)).isChecked()) points += 2;
        if (((RadioButton) findViewById(R.id.q3_option2)).isChecked()) points += 2;
        if (((RadioButton) findViewById(R.id.q4_option2)).isChecked()) points += 2;
        if (((RadioButton) findViewById(R.id.q5_option1)).isChecked()) points += 2;

        int additionalPoints = 0;
        if (((CheckBox) findViewById(R.id.workExperience)).isChecked()) additionalPoints++;
        if (((CheckBox) findViewById(R.id.teamworkSkills)).isChecked()) additionalPoints++;
        if (((CheckBox) findViewById(R.id.willingnessToTravel)).isChecked()) additionalPoints++;

        int totalPoints = points + additionalPoints;

        StringBuilder result = new StringBuilder();
        if (!isAgeValid || !isSalaryValid) {
            result.append("Не пройдено вимоги компанії:\n");
            if (!isAgeValid) result.append("- Вік не в межах 21-40\n");
            if (!isSalaryValid) result.append("- ЗП поза діапазоном 1500-2000$\n");
        } else {
            result.append("Вимоги компанії пройдено!\n")
                    .append("Набрано балів: ").append(totalPoints).append("/13\n")
                    .append(totalPoints >= 10 ? "Успішне проходження!" : "Недостатньо балів!");
        }

        new AlertDialog.Builder(this)
                .setTitle("Результат")
                .setMessage(result.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    private String getSalaryText(int progress) {
        return "$" + (1200 + progress * 10);
    }

    private void updateTextViewPosition(SeekBar seekBar, int progress) {
        int seekBarWidth = seekBar.getWidth();
        int thumbOffset = seekBar.getThumbOffset();
        int thumbPosition = thumbOffset + progress
                * (seekBarWidth - seekBar.getPaddingLeft() - seekBar.getPaddingRight()) / seekBar.getMax();
        salaryValueText.setX(seekBar.getX() + seekBar.getPaddingLeft()
                + thumbPosition - (float) salaryValueText.getWidth() / 2);
    }
}
