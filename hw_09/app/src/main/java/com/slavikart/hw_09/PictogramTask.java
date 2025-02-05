package com.slavikart.hw_09;

public class PictogramTask {
    private int imageId;
    private String answer;
    private int difficulty;

    public PictogramTask(int imageId, String answer, int difficulty) {
        this.imageId = imageId;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAnswer() {
        return answer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean checkAnswer(String userAnswer) {
        return answer.equalsIgnoreCase(userAnswer);
    }
}
