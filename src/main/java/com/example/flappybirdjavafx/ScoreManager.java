package com.example.flappybirdjavafx;

import javafx.scene.control.Label;

public class ScoreManager {

    private BirdController birdController;
    private Label scoreLabel;
    private int score = 0;
    private String startText;
    public ScoreManager(BirdController birdController, Label scoreLabel) {
        this.birdController = birdController;
        this.scoreLabel = scoreLabel;
        this.startText = scoreLabel.getText();
    }

    public void update() {
        updateScore();
        scoreLabel.toFront();
    }

    private void toFront() {
    }

    private void updateScore() {
        if(birdController.getObstacleManager().checkObstaclePassed()) {
            score++;
            scoreLabel.setText("Score: " + score);
        }
    }
    public void reset() {
        this.score = 0;
        scoreLabel.setText(startText);
    }
}
