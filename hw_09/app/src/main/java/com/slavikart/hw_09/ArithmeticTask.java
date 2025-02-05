package com.slavikart.hw_09;

import java.util.Random;

public class ArithmeticTask {
    private int num1;
    private int num2;
    private String operator;
    private int answer;

    public ArithmeticTask(int difficulty) {
        Random rand = new Random();
        String[] operators;

        switch(difficulty) {
            case 0: // Easy
                operators = new String[]{"+", "-"};
                num1 = rand.nextInt(20);
                num2 = rand.nextInt(20);
                break;
            case 1: // Medium
                operators = new String[]{"+", "-", "*"};
                num1 = rand.nextInt(50);
                num2 = rand.nextInt(50);
                break;
            default: // Hard
                operators = new String[]{"+", "-", "*", "/"};
                num1 = rand.nextInt(100);
                num2 = rand.nextInt(100) + 1;
                break;
        }
        operator = operators[rand.nextInt(operators.length)];
        calculateAnswer();
    }

    private void calculateAnswer() {
        switch(operator) {
            case "+": answer = num1 + num2; break;
            case "-": answer = num1 - num2; break;
            case "*": answer = num1 * num2; break;
            case "/": answer = num1 / num2; break;
        }
    }

    public String getQuestion() {
        return num1 + " " + operator + " " + num2 + " = ?";
    }

    public int getAnswer() {
        return answer;
    }

    public boolean checkAnswer(int userAnswer) {
        return userAnswer == answer;
    }
}
