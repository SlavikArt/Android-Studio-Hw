package com.slavikart.hw_03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private StringBuilder input = new StringBuilder();
    private double num1 = 0;
    private String op = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.editText);
        setupBtns();
    }

    private void setupBtns() {
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnClear = findViewById(R.id.buttonClear);
        Button btnDot = findViewById(R.id.buttonDot);
        Button btnAdd = findViewById(R.id.buttonAdd);
        Button btnSub = findViewById(R.id.buttonSubtract);
        Button btnMul = findViewById(R.id.buttonMultiply);
        Button btnDiv = findViewById(R.id.buttonDivide);
        Button btnEq = findViewById(R.id.buttonEqual);
        Button btnBack = findViewById(R.id.buttonBack);
        Button btnSqrt = findViewById(R.id.buttonSqrt);
        Button btnPow = findViewById(R.id.buttonPow);

        Button[] numBtns = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for (int i = 0; i <= 9; i++) {
            int finalI = i;
            numBtns[i].setOnClickListener(v -> appendNum(finalI));
        }

        btnDot.setOnClickListener(v -> appendDot());
        btnClear.setOnClickListener(v -> clear());
        btnBack.setOnClickListener(v -> backspace());
        btnAdd.setOnClickListener(v -> setOp("+"));
        btnSub.setOnClickListener(v -> setOp("-"));
        btnMul.setOnClickListener(v -> setOp("*"));
        btnDiv.setOnClickListener(v -> setOp("/"));
        btnEq.setOnClickListener(v -> calc());
        btnSqrt.setOnClickListener(v -> applySqrt());
        btnPow.setOnClickListener(v -> setOp("^"));
    }

    private void appendNum(int n) {
        input.append(n);
        updateInputField();
    }

    private void appendDot() {
        if (!input.toString().contains(".")) {
            input.append(".");
            updateInputField();
        }
    }

    private void backspace() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            updateInputField();
        }
    }

    private void clear() {
        input.setLength(0);
        num1 = 0;
        op = "";
        updateInputField();
    }

    private void setOp(String operator) {
        if (input.length() > 0) {
            num1 = Double.parseDouble(input.toString());
            op = operator;
            input.setLength(0);
        }
    }

    private void calc() {
        if (op.isEmpty() || input.length() == 0) return;

        double num2 = Double.parseDouble(input.toString());
        double result = 0;

        switch (op) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    inputField.setText(R.string.cannot_divide_by_zero);
                    return;
                }
                break;
            case "^":
                result = Math.pow(num1, num2);
                break;
        }

        input.setLength(0);
        input.append(result);
        op = "";
        updateInputField();
    }

    private void applySqrt() {
        if (input.length() > 0) {
            double num = Double.parseDouble(input.toString());
            input.setLength(0);
            input.append(Math.sqrt(num));
            updateInputField();
        }
    }

    private void updateInputField() {
        inputField.setText(input.toString());
    }
}
